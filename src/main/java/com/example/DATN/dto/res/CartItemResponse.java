package com.example.DATN.dto.res;

public class CartItemResponse {
    private Long id;

    private CartResponse cart;

    private FoodResponse food;

    private Integer quantity;

    private String note;

    public CartItemResponse() {
    }

    public CartItemResponse(Long id, CartResponse cart, FoodResponse food, Integer quantity, String note) {
        this.id = id;
        this.cart = cart;
        this.food = food;
        this.quantity = quantity;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartResponse getCart() {
        return cart;
    }

    public void setCart(CartResponse cart) {
        this.cart = cart;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
