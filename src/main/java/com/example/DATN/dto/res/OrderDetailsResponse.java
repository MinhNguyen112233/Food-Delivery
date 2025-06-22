package com.example.DATN.dto.res;

import com.example.DATN.entities.Food;
import com.example.DATN.entities.Orders;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class OrderDetailsResponse {

    private Long id;

    private OrdersResponse order;

    private FoodResponse food;

    private Integer quantity;

    private BigDecimal price;

    private String note;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(Long id, OrdersResponse order, FoodResponse food, Integer quantity, BigDecimal price) {
        this.id = id;
        this.order = order;
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdersResponse getOrder() {
        return order;
    }

    public void setOrder(OrdersResponse order) {
        this.order = order;
    }

    public FoodResponse getFood() {
        return food;
    }

    public void setFood(FoodResponse food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
