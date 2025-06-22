package com.example.DATN.dto.req;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.entities.PaymentMethod;
import com.example.DATN.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersRequest {

    private Long user_id;

    private Long address_id;

    private Long food_shop_id;

    private Long user_voucher_id;

    private String note;

    @JsonProperty("isDeliveryDoor")
    private boolean isDeliveryDoor;

    @JsonProperty("isEatingUtensils")
    private boolean isEatingUtensils;

    @JsonProperty("isFavourite")
    private boolean isFavourite;

    private BigDecimal totalPrice;

    private BigDecimal totalOrderItem;

    private BigDecimal totalDiscount;

    private BigDecimal totalDelivery;

    private BigDecimal totalApply;

    private String status ;

    private String paymentMethod ;

    private double distanceKm;

    private String estimatedDeliveryTime;

    private LocalDateTime expectedTime;

    public OrdersRequest() {
    }

    public OrdersRequest(Long user_id, Long address_id, BigDecimal totalPrice, String status, String paymentMethod) {
        this.user_id = user_id;
        this.address_id = address_id;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getFood_shop_id() {
        return food_shop_id;
    }

    public void setFood_shop_id(Long food_shop_id) {
        this.food_shop_id = food_shop_id;
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

    public Long getUser_voucher_id() {
        return user_voucher_id;
    }

    public void setUser_voucher_id(Long user_voucher_id) {
        this.user_voucher_id = user_voucher_id;
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
