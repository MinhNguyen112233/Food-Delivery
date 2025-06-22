package com.example.DATN.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "food_shop_id", nullable = false)
    private FoodShop foodShop;

    @ManyToOne
    @JoinColumn(name = "user_voucher_id")
    private UserVoucher userVoucher;

    private BigDecimal totalPrice;

    private BigDecimal totalOrderItem;

    private BigDecimal totalDiscount;

    private BigDecimal totalDelivery;

    private BigDecimal totalApply;

    private String note;

    private boolean isDeliveryDoor;

    private boolean isEatingUtensils;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    private boolean isFavourite;

    private double distanceKm;

    private String estimatedDeliveryTime;

    private LocalDateTime expectedTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Orders() {
    }

    public Orders(Long id,  User user, Address address, FoodShop foodShop, BigDecimal totalPrice, BigDecimal totalOrderItem, BigDecimal totalDiscount, BigDecimal totalDelivery, BigDecimal totalApply, String note, boolean isDeliveryDoor, boolean isEatingUtensils, OrderStatus status, PaymentMethod paymentMethod, boolean isFavourite, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
       // this.orderCode = orderCode;
        this.user = user;
        this.address = address;
        this.foodShop = foodShop;
        this.totalPrice = totalPrice;
        this.totalOrderItem = totalOrderItem;
        this.totalDiscount = totalDiscount;
        this.totalDelivery = totalDelivery;
        this.totalApply = totalApply;
        this.note = note;
        this.isDeliveryDoor = isDeliveryDoor;
        this.isEatingUtensils = isEatingUtensils;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.isFavourite = isFavourite;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FoodShop getFoodShop() {
        return foodShop;
    }

    public void setFoodShop(FoodShop foodShop) {
        this.foodShop = foodShop;
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

    public UserVoucher getUserVoucher() {
        return userVoucher;
    }

    public void setUserVoucher(UserVoucher userVoucher) {
        this.userVoucher = userVoucher;
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

    @PrePersist
    protected void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();  // Lấy thời gian hiện tại dưới dạng Date
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onPreUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }
}
