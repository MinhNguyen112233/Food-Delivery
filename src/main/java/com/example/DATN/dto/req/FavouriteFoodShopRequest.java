package com.example.DATN.dto.req;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class FavouriteFoodShopRequest {
    private Long userId;

    private Long foodShopId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFoodShopId() {
        return foodShopId;
    }

    public void setFoodShopId(Long foodShopId) {
        this.foodShopId = foodShopId;
    }
}
