package com.example.DATN.dto.res;

import com.example.DATN.entities.Food;
import com.example.DATN.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class ReviewsResponse {

    private Long id;

    private UserResponse user;

    private FoodResponse food;

    private Long order_detail_id;

    private BigDecimal rating;

    private String comment;

    public ReviewsResponse() {
    }

    public ReviewsResponse(Long id, UserResponse user, FoodResponse food, BigDecimal rating, String comment) {
        this.id = id;
        this.user = user;
        this.food = food;
        this.rating = rating;
        this.comment = comment;
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

    public FoodResponse getFood() {
        return food;
    }

    public void setFood(FoodResponse food) {
        this.food = food;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(Long order_detail_id) {
        this.order_detail_id = order_detail_id;
    }
}
