package com.example.DATN.dto.res;

import com.example.DATN.entities.FoodShop;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FoodResponse {

    private Long id;

    private FoodShopResponse foodShop;

    private String name;

    private String description;

    private Double price;

    private Double discount;

    private Double rating;

    private Integer totalReviews;

    private Integer totalSell;

    private String image;

    private Boolean isTrending;

    public FoodResponse() {
    }

    public FoodResponse(Long id, FoodShopResponse foodShop, String name, String description, Double price, Double discount, Double rating, Integer totalReviews, String image, Boolean isTrending) {
        this.id = id;
        this.foodShop = foodShop;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.image = image;
        this.isTrending = isTrending;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodShopResponse getFoodShop() {
        return foodShop;
    }

    public void setFoodShop(FoodShopResponse foodShop) {
        this.foodShop = foodShop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getTrending() {
        return isTrending;
    }

    public void setTrending(Boolean trending) {
        isTrending = trending;
    }

    public Integer getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(Integer totalSell) {
        this.totalSell = totalSell;
    }
}
