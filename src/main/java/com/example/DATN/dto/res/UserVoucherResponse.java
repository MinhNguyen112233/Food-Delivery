package com.example.DATN.dto.res;

import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UserVoucherResponse {
    private Long id;

    private Long userId;

    private VoucherResponse voucher;

    private UserVoucher.VoucherStatus status ;

    private LocalDateTime usedAt;

    public UserVoucherResponse() {
    }

    public UserVoucherResponse(Long id, Long userId, VoucherResponse voucher, UserVoucher.VoucherStatus status, LocalDateTime usedAt) {
        this.id = id;
        this.userId = userId;
        this.voucher = voucher;
        this.status = status;
        this.usedAt = usedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VoucherResponse getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherResponse voucher) {
        this.voucher = voucher;
    }

    public UserVoucher.VoucherStatus getStatus() {
        return status;
    }

    public void setStatus(UserVoucher.VoucherStatus status) {
        this.status = status;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
}
