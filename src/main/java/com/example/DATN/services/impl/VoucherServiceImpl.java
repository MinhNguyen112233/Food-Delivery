package com.example.DATN.services.impl;

import com.example.DATN.dto.req.VoucherRequest;
import com.example.DATN.dto.res.PublicVoucherResponse;
import com.example.DATN.dto.res.UserVoucherResponse;
import com.example.DATN.dto.res.VoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import com.example.DATN.mapper.VoucherMapper;
import com.example.DATN.repositories.UserVoucherRepo;
import com.example.DATN.repositories.VoucherRepo;
import com.example.DATN.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepo voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private UserVoucherRepo userVoucherRepository;

    @Override
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(voucherMapper::VoucherToVoucherResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherResponse getVoucherById(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow();
        return voucherMapper.VoucherToVoucherResponse(voucher);
    }

    @Override
    public VoucherResponse getVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code);
        return voucherMapper.VoucherToVoucherResponse(voucher);
    }

    @Override
    public List<VoucherResponse> getActiveVouchers() {
        List<Voucher> activeVouchers = voucherRepository.findByEndDateAfter(LocalDateTime.now());
        return activeVouchers.stream()
                .map(voucherMapper::VoucherToVoucherResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VoucherResponse createVoucher(VoucherRequest voucherRequest) {

        Voucher voucher = voucherMapper.VoucherRequestToVoucher(voucherRequest);
        Voucher savedVoucher = voucherRepository.save(voucher);

        savedVoucher.setCode("MFOOD " + savedVoucher.getId());

        // Update lại voucher với code mới
        Voucher updatedVoucher = voucherRepository.save(savedVoucher);

        return voucherMapper.VoucherToVoucherResponse(updatedVoucher);
    }

    @Override
    public VoucherResponse updateVoucher(Long id, VoucherRequest voucherRequest) {
        Voucher existingVoucher = voucherRepository.findById(id).orElseThrow();

        // Check if the code is being changed and if it already exists
//        if (!existingVoucher.getCode().equals(voucherRequest.getCode()) && voucherRepository.existsByCode(voucherRequest.getCode())) {
//            throw new DuplicateResourceException("Voucher code already exists: " + voucherRequest.getCode());
//        }

        Voucher updatedVoucher = voucherRepository.save(existingVoucher);
        return voucherMapper.VoucherToVoucherResponse(updatedVoucher);
    }

    @Override
    public void deleteVoucher(Long id) {

        voucherRepository.deleteById(id);
    }

    @Override
    public List<VoucherResponse> listVoucherActive(BigDecimal minOrderValue, LocalDateTime currentDate) {
        List<Voucher> activeVouchers = voucherRepository.findByMinOrderValueLessThanEqualAndEndDateAfter(minOrderValue, currentDate);
        return activeVouchers.stream()
                .map(voucherMapper::VoucherToVoucherResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherResponse> filterValidVouchers(Long userId, BigDecimal orderValue, LocalDateTime currentDate) {

        List<Voucher> vouchers = userVoucherRepository.findVouchersByUserIdAndStatus(userId, UserVoucher.VoucherStatus.AVAILABLE);

        List<Voucher> voucherList = vouchers.stream()
                .filter(voucher -> voucher.getMinOrderValue().compareTo(orderValue) <= 0) // minOrderValue <= orderValue
                .filter(voucher -> voucher.getEndDate().isAfter(currentDate)) // endDate > currentDate
                .collect(Collectors.toList());

        return voucherList.stream().map(voucherMapper::VoucherToVoucherResponse).collect(Collectors.toList());
    }




    @Override
    public List<VoucherResponse> getVoucherStatus(Long userId, UserVoucher.VoucherStatus status) {
        List<Voucher> vouchers = userVoucherRepository.findVouchersByUserIdAndStatus(userId, status);
        return vouchers.stream().map(voucherMapper::VoucherToVoucherResponse).collect(Collectors.toList());
    }

    @Override
    public List<VoucherResponse> getVoucherWelcome() {
        List<Voucher> vouchers = voucherRepository.findByType(Voucher.VoucherType.WELCOME);
        return vouchers.stream().map(voucherMapper::VoucherToVoucherResponse).collect(Collectors.toList());
    }

    @Override
    public List<PublicVoucherResponse> getPublicVouchersWithUser(Long userId) {
        // Lấy toàn bộ vouchers có type = PUBLIC
        List<Voucher> publicVouchers = voucherRepository.findByType(Voucher.VoucherType.PUBLIC);

        // Lấy toàn bộ voucherId mà user đã lưu (có trong bảng user_vouchers)
        List<Long> userVoucherIds = userVoucherRepository.findVoucherIdsByUserId(userId);

        // Map danh sách voucher -> PublicVoucherResponse (có isSave = true nếu user đã lưu voucher đó)
        return publicVouchers.stream()
                .map(voucher -> {
                    VoucherResponse response = voucherMapper.VoucherToVoucherResponse(voucher);
                    boolean isSaved = userVoucherIds.contains(voucher.getId());
                    return new PublicVoucherResponse(response, isSaved);
                })
                .collect(Collectors.toList());
    }


}
