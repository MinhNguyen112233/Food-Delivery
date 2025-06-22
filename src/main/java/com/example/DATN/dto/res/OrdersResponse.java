package com.example.DATN.dto.res;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.entities.PaymentMethod;
import com.example.DATN.entities.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersResponse {

    private Long id;

    private Long userId;

    private String orderCode;

    private Long userVoucherId;

    private AddressResponse addressResponse;

    private FoodShopResponse foodShopResponse;

    private BigDecimal totalPrice;

    private BigDecimal totalOrderItem;

    private BigDecimal totalDiscount;

    private BigDecimal totalDelivery;

    private BigDecimal totalApply;

    private String note;

    private boolean isDeliveryDoor;

    private boolean isEatingUtensils;

    private OrderStatus status ;

    private PaymentMethod paymentMethod ;

    private boolean isFavourite;

    private double distanceKm;

    private String estimatedDeliveryTime;

    private LocalDateTime expectedTime;

    private LocalDateTime createdAt;

    public OrdersResponse() {
    }

    public OrdersResponse(Long id, Long userId, AddressResponse addressResponse, FoodShopResponse foodShopResponse, BigDecimal totalPrice, OrderStatus status, PaymentMethod paymentMethod, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.addressResponse = addressResponse;
        this.foodShopResponse = foodShopResponse;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
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

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }

    public FoodShopResponse getFoodShopResponse() {
        return foodShopResponse;
    }

    public void setFoodShopResponse(FoodShopResponse foodShopResponse) {
        this.foodShopResponse = foodShopResponse;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDeliveryDoor() {
        return isDeliveryDoor;
    }

    public void setDeliveryDoor(boolean deliveryDoor) {
        isDeliveryDoor = deliveryDoor;
    }

    public boolean isEatingUtensils() {
        return isEatingUtensils;
    }

    public void setEatingUtensils(boolean eatingUtensils) {
        isEatingUtensils = eatingUtensils;
    }

    public BigDecimal getTotalOrderItem() {
        return totalOrderItem;
    }

    public void setTotalOrderItem(BigDecimal totalOrderItem) {
        this.totalOrderItem = totalOrderItem;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getTotalDelivery() {
        return totalDelivery;
    }

    public void setTotalDelivery(BigDecimal totalDelivery) {
        this.totalDelivery = totalDelivery;
    }

    public BigDecimal getTotalApply() {
        return totalApply;
    }

    public void setTotalApply(BigDecimal totalApply) {
        this.totalApply = totalApply;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getUserVoucherId() {
        return userVoucherId;
    }

    public void setUserVoucherId(Long userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public LocalDateTime getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(LocalDateTime expectedTime) {
        this.expectedTime = expectedTime;
    }
}
