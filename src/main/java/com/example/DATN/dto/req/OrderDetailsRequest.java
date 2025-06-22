package com.example.DATN.dto.req;

import com.example.DATN.entities.Food;
import com.example.DATN.entities.Orders;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class OrderDetailsRequest {

    private Long order_id;

    private Long food_id;

    private Integer quantity;

    private BigDecimal price;

    private String note;

    public OrderDetailsRequest() {
    }

    public OrderDetailsRequest(Long order_id, Long food_id, Integer quantity, BigDecimal price) {
        this.order_id = order_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
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
