package com.example.DATN.dto.res;

import com.example.DATN.entities.FoodShop;
import com.example.DATN.entities.User;
import jakarta.persistence.*;

public class CartResponse {

    private Long id;

    private UserResponse user;

    private FoodShopResponse foodShop;

    public CartResponse() {
    }

    public CartResponse(Long id, UserResponse user, FoodShopResponse foodShop) {
        this.id = id;
        this.user = user;
        this.foodShop = foodShop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public FoodShopResponse getFoodShop() {
        return foodShop;
    }

    public void setFoodShop(FoodShopResponse foodShop) {
        this.foodShop = foodShop;
    }
}
