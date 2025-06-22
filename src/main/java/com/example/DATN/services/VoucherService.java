package com.example.DATN.services;

import com.example.DATN.dto.req.VoucherRequest;
import com.example.DATN.dto.res.PublicVoucherResponse;
import com.example.DATN.dto.res.VoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public interface VoucherService {
    List<VoucherResponse> getAllVouchers();

    VoucherResponse getVoucherById(Long id);

    VoucherResponse getVoucherByCode(String code);

    List<VoucherResponse> getActiveVouchers();

    VoucherResponse createVoucher(VoucherRequest voucherRequest);

    VoucherResponse updateVoucher(Long id, VoucherRequest voucherRequest);

    void deleteVoucher(Long id);

    List<VoucherResponse> listVoucherActive(BigDecimal minOrderValue, LocalDateTime currentDate);

    List<VoucherResponse> filterValidVouchers(Long userId, BigDecimal orderValue, LocalDateTime currentDate);

    List<VoucherResponse> getVoucherStatus(Long userId , UserVoucher.VoucherStatus status);

    List<VoucherResponse> getVoucherWelcome();

    List<PublicVoucherResponse> getPublicVouchersWithUser(Long userId);
}
