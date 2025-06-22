package com.example.DATN.dto.req;

import com.example.DATN.entities.Food;
import com.example.DATN.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class ReviewsRequest {

    private Long user_id;

    private Long food_id;

    private Long order_detail_id;

    private BigDecimal rating;

    private String comment;

    public ReviewsRequest() {
    }

    public ReviewsRequest(Long user_id, Long food_id, BigDecimal rating, String comment) {
        this.user_id = user_id;
        this.food_id = food_id;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
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
