package com.example.DATN.dto.req;

import com.example.DATN.entities.FoodShop;
import com.example.DATN.entities.User;
import jakarta.persistence.*;

public class CartRequest {

    private Long user_id;

    private Long foodShop_id;

    public CartRequest(Long foodShop_id, Long user_id) {
        this.foodShop_id = foodShop_id;
        this.user_id = user_id;
    }

    public CartRequest() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getFoodShop_id() {
        return foodShop_id;
    }

    public void setFoodShop_id(Long foodShop_id) {
        this.foodShop_id = foodShop_id;
    }
}
