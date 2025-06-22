package com.example.DATN.dto.res;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class FavouriteFoodShopResponse {

        private Long id;

        private Long userId;

        private FoodShopResponse foodShopResponse;

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

    public FoodShopResponse getFoodShopResponse() {
        return foodShopResponse;
    }

    public void setFoodShopResponse(FoodShopResponse foodShopResponse) {
        this.foodShopResponse = foodShopResponse;
    }
}
