package com.example.DATN.dto.req;

import com.example.DATN.entities.Cart;
import com.example.DATN.entities.Food;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CartItemRequest {

    private Long cart_id;

    private Long food_id;

    private Integer quantity;

    private String note;

    public CartItemRequest(Long cart_id, Long food_id, Integer quantity, String note) {
        this.cart_id = cart_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.note = note;
    }

    public CartItemRequest() {
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
