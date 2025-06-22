package com.example.DATN.services;

import com.example.DATN.dto.req.UserVoucherRequest;
import com.example.DATN.dto.res.UserVoucherResponse;
import com.example.DATN.entities.UserVoucher;

import java.time.LocalDateTime;
import java.util.List;

public interface UserVoucherService {
    List<UserVoucherResponse> getAllUserVouchers();

    UserVoucherResponse getUserVoucherById(Long id);

    List<UserVoucherResponse> getUserVouchersByUserId(Long userId);

    List<UserVoucherResponse> getVouchersByUserIdAndStatus(Long userId, UserVoucher.VoucherStatus status);

    UserVoucherResponse createUserVoucher(UserVoucherRequest request);

    UserVoucherResponse updateUserVoucher(Long id, UserVoucherRequest request);

    void deleteUserVoucher(Long id);

    UserVoucherResponse useVoucher(Long id);

    UserVoucherResponse getUserVoucherByUserIdAndCode(Long id, String code, UserVoucher.VoucherStatus status);

    List<UserVoucherResponse> findAvailableVouchersWithStreamFilter(Long userId, double minOrderValue, LocalDateTime endDate);
}
