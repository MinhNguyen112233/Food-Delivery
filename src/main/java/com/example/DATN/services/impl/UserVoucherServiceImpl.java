package com.example.DATN.services.impl;

import com.example.DATN.dto.req.UserVoucherRequest;
import com.example.DATN.dto.res.UserVoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import com.example.DATN.mapper.UserVoucherMapper;
import com.example.DATN.repositories.UserVoucherRepo;
import com.example.DATN.repositories.VoucherRepo;
import com.example.DATN.services.UserVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserVoucherServiceImpl implements UserVoucherService {

    @Autowired
    private UserVoucherRepo userVoucherRepository;

    @Autowired
    private VoucherRepo voucherRepository;

    @Autowired
    private UserVoucherMapper userVoucherMapper;

    /**
     * Get all user vouchers in the system
     */
    @Override
    public List<UserVoucherResponse> getAllUserVouchers() {
        List<UserVoucher> userVouchers = userVoucherRepository.findAll();
        return userVouchers.stream()
                .map(userVoucherMapper::UserVoucherToUserVoucherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a user voucher by ID
     */
    @Override
    public UserVoucherResponse getUserVoucherById(Long id) {
        UserVoucher userVoucher = userVoucherRepository.findById(id)
                .orElseThrow();
        return userVoucherMapper.UserVoucherToUserVoucherResponse(userVoucher);
    }

    /**
     * Get all vouchers for a specific user
     */
    @Override
    public List<UserVoucherResponse> getUserVouchersByUserId(Long userId) {
        List<UserVoucher> userVouchers = userVoucherRepository.findByUserId(userId);
        return userVouchers.stream()
                .map(userVoucherMapper::UserVoucherToUserVoucherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get only available vouchers for a specific user
     */
    @Override
    public List<UserVoucherResponse> getVouchersByUserIdAndStatus(Long userId, UserVoucher.VoucherStatus status) {
        List<UserVoucher> userVouchers = userVoucherRepository.findByUserIdAndStatus(
                userId, status);
        return userVouchers.stream()
                .map(userVoucherMapper::UserVoucherToUserVoucherResponse)
                .collect(Collectors.toList());
    }

    /**
     * Create a new user voucher
     */

    @Override
    public UserVoucherResponse createUserVoucher(UserVoucherRequest request) {
        // Find the voucher or throw exception if not found
        Voucher voucher = voucherRepository.findById(request.getVoucherId())
                .orElseThrow();

        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUserId(request.getUserId());
        userVoucher.setVoucher(voucher);

        // Set status if provided, otherwise default to AVAILABLE
        if (request.getStatus() != null) {
            userVoucher.setStatus(request.getStatus());
        }

        // Set usedAt if provided and status is USED
        if (request.getUsedAt() != null && UserVoucher.VoucherStatus.USED.equals(request.getStatus())) {
            userVoucher.setUsedAt(request.getUsedAt());
        }

        UserVoucher savedUserVoucher = userVoucherRepository.save(userVoucher);
        return userVoucherMapper.UserVoucherToUserVoucherResponse(userVoucher);
    }

    /**
     * Update an existing user voucher
     */

    @Override
    public UserVoucherResponse updateUserVoucher(Long id, UserVoucherRequest request) {
        UserVoucher userVoucher = userVoucherRepository.findById(id)
                .orElseThrow();

        // Update voucher if voucherId changed
        if (request.getVoucherId() != null &&
                (userVoucher.getVoucher() == null || !userVoucher.getVoucher().getId().equals(request.getVoucherId()))) {
            Voucher voucher = voucherRepository.findById(request.getVoucherId())
                    .orElseThrow();
            userVoucher.setVoucher(voucher);
        }

        // Update userId if provided
        if (request.getUserId() != null) {
            userVoucher.setUserId(request.getUserId());
        }

        // Update status if provided
        if (request.getStatus() != null) {
            userVoucher.setStatus(request.getStatus());
        }

        // Update usedAt if provided
        if (request.getUsedAt() != null) {
            userVoucher.setUsedAt(request.getUsedAt());
        } else if (UserVoucher.VoucherStatus.USED.equals(request.getStatus()) && userVoucher.getUsedAt() == null) {
            // If status is changed to USED and usedAt is not set, set it to current time
            userVoucher.setUsedAt(LocalDateTime.now());
        }

        UserVoucher updatedUserVoucher = userVoucherRepository.save(userVoucher);
        return userVoucherMapper.UserVoucherToUserVoucherResponse(userVoucher);
    }

    /**
     * Delete a user voucher
     */

    @Override
    public void deleteUserVoucher(Long id) {

        userVoucherRepository.deleteById(id);
    }

    /**
     * Mark a voucher as used
     */

    @Override
    public UserVoucherResponse useVoucher(Long id) {
        UserVoucher userVoucher = userVoucherRepository.findById(id)
                .orElseThrow();

        if (userVoucher.getStatus() != UserVoucher.VoucherStatus.AVAILABLE) {
            throw new IllegalStateException("Voucher is not available for use");
        }

        // Check if voucher has expired based on the voucher's end date
        if (userVoucher.getVoucher().getEndDate().isBefore(LocalDateTime.now())) {
            userVoucher.setStatus(UserVoucher.VoucherStatus.EXPIRED);
            userVoucherRepository.save(userVoucher);
            throw new IllegalStateException("Voucher has expired");
        }

        userVoucher.setStatus(UserVoucher.VoucherStatus.USED);
        userVoucher.setUsedAt(LocalDateTime.now());

        UserVoucher updatedUserVoucher = userVoucherRepository.save(userVoucher);
        return userVoucherMapper.UserVoucherToUserVoucherResponse(userVoucher);
    }

    @Override
    public UserVoucherResponse getUserVoucherByUserIdAndCode(Long id, String code, UserVoucher.VoucherStatus status) {
        UserVoucher userVoucher = userVoucherRepository.findByVoucherCodeAndUserIdAndStatus(code, id, status);
        return userVoucherMapper.UserVoucherToUserVoucherResponse(userVoucher);
    }

    @Override
//    public List<UserVoucherResponse> findAvailableVouchersWithStreamFilter(Long userId, double minOrderValue, LocalDateTime endDate) {
//        // Lấy tất cả UserVoucher AVAILABLE của user
//        List<UserVoucher> allUserVouchers = userVoucherRepository.findAll()
//                .stream()
//                .filter(uv -> uv.getUserId().equals(userId))
//                .filter(uv -> uv.getStatus() == UserVoucher.VoucherStatus.AVAILABLE)
//                .toList();
//
//        LocalDateTime currentDate = LocalDateTime.now();
//        BigDecimal orderValue = BigDecimal.valueOf(minOrderValue);
//
//        List<UserVoucher> response =  allUserVouchers.stream()
//                .filter(userVoucher -> {
//                    Voucher voucher = userVoucher.getVoucher();
//                    // Filter: minOrderValue <= orderValue
//                    return voucher.getMinOrderValue() == null ||
//                            voucher.getMinOrderValue().compareTo(orderValue) <= 0;
//                })
//                .filter(userVoucher -> {
//                    Voucher voucher = userVoucher.getVoucher();
//                    // Filter: endDate > currentDate (voucher chưa hết hạn)
//                    return voucher.getEndDate().isAfter(currentDate);
//                })
//                .filter(userVoucher -> {
//                    Voucher voucher = userVoucher.getVoucher();
//                    // Filter: startDate <= currentDate (voucher đã có hiệu lực)
//                    return voucher.getStartDate().isBefore(currentDate) ||
//                            voucher.getStartDate().isEqual(currentDate);
//                })
//                .filter(userVoucher -> {
//                    if (endDate == null) return true;
//                    Voucher voucher = userVoucher.getVoucher();
//                    // Optional filter: voucher.endDate <= endDate
//                    return voucher.getEndDate().isBefore(endDate) ||
//                            voucher.getEndDate().isEqual(endDate);
//                })
//                .toList();
//        return response.stream().map(userVoucherMapper :: UserVoucherToUserVoucherResponse).collect(Collectors.toList());
//    }
    public List<UserVoucherResponse> findAvailableVouchersWithStreamFilter(Long userId, double minOrderValue, LocalDateTime endDate) {
        // Lấy tất cả UserVoucher AVAILABLE của user
        List<UserVoucher> allUserVouchers = userVoucherRepository.findAll()
                .stream()
                .filter(uv -> uv.getUserId().equals(userId))
                .filter(uv -> uv.getStatus() == UserVoucher.VoucherStatus.AVAILABLE)
                .toList();

        LocalDateTime currentDate = LocalDateTime.now();
        BigDecimal orderValue = BigDecimal.valueOf(minOrderValue);

        List<UserVoucher> response = allUserVouchers.stream()
                .filter(userVoucher -> {
                    Voucher voucher = userVoucher.getVoucher();
                    // Filter: orderValue >= minOrderValue (giá trị truyền vào lớn hơn hoặc bằng minOrderValue của voucher)
                    return voucher.getMinOrderValue() == null ||
                            orderValue.compareTo(voucher.getMinOrderValue()) >= 0;
                })
                .filter(userVoucher -> {
                    Voucher voucher = userVoucher.getVoucher();
                    // Filter: endDate > currentDate (voucher chưa hết hạn)
                    return voucher.getEndDate().isAfter(currentDate);
                })
                .filter(userVoucher -> {
                    Voucher voucher = userVoucher.getVoucher();
                    // Filter: startDate <= currentDate (voucher đã có hiệu lực)
                    return voucher.getStartDate().isBefore(currentDate) ||
                            voucher.getStartDate().isEqual(currentDate);
                })

                .toList();

        return response.stream()
                .map(userVoucherMapper::UserVoucherToUserVoucherResponse)
                .collect(Collectors.toList());
    }


}
