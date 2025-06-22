package com.example.DATN.dto.res;

import com.example.DATN.entities.Voucher;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoucherResponse {
    private Long id;

    private String title;

    private String description;

    private Voucher.DiscountType discountType; // theo phần trăm hoặc giá cố định

    private Voucher.VoucherType voucherType;

    private BigDecimal discountValue; // giá trị giảm giá %

    private String code;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal minOrderValue; // giá trị đơn hàng tối thiểu

    private BigDecimal maxDiscountAmount;

    public VoucherResponse() {
    }

    public VoucherResponse(Long id, String title, String description, Voucher.DiscountType discountType, BigDecimal discountValue, String code, LocalDateTime startDate, LocalDateTime endDate, BigDecimal minOrderValue, BigDecimal maxDiscountAmount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minOrderValue = minOrderValue;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Voucher.DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Voucher.DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public BigDecimal getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public Voucher.VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(Voucher.VoucherType voucherType) {
        this.voucherType = voucherType;
    }
}
