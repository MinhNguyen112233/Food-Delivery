package com.example.DATN.dto.req;

import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UserVoucherRequest {

    private Long userId;

    private Long voucherId;

    private UserVoucher.VoucherStatus status ;

    private LocalDateTime usedAt;

    public UserVoucherRequest() {
    }

    public UserVoucherRequest(Long userId, Long voucherId, UserVoucher.VoucherStatus status, LocalDateTime usedAt) {
        this.userId = userId;
        this.voucherId = voucherId;
        this.status = status;
        this.usedAt = usedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
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
