package com.example.DATN.entities;

public enum OrderStatus {
    PENDING,
    CONFIRM_BY_SHIPPER,
    CONFIRM_BY_SHOP,
    PREPARING,
    WAITING_FOR_SHIPPER,
    DELIVERING,
    COMPLETED,
    CANCELLED
}
