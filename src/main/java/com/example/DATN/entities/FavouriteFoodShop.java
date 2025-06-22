package com.example.DATN.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "favourite_food_shop")
public class FavouriteFoodShop {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long foodShopId;

    public FavouriteFoodShop() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getFoodShopId() {
        return foodShopId;
    }

    public void setFoodShopId(Long foodShopId) {
        this.foodShopId = foodShopId;
    }
}
