package com.example.DATN.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class NotificationFcm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String title;
    private String body;
    private String foodShopIds;
    private LocalDateTime createdAt;

    public NotificationFcm() {
    }

    public NotificationFcm(Long id, String image, String title, String body, String foodShopIds, LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.body = body;
        this.foodShopIds = foodShopIds;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFoodShopIds() {
        return foodShopIds;
    }

    public void setFoodShopIds(String foodShopIds) {
        this.foodShopIds = foodShopIds;
    }
}
