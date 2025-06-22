package com.example.DATN.dto.req;

import com.example.DATN.entities.FoodShop;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FoodRequest {

    private Long foodShop_id;

    private String name;

    private Long category_id;

    private String description;

    private Double price;

    private Double discount;

    private String image;

    private Boolean isTrending;

    public FoodRequest() {
    }

    public FoodRequest(Long foodShop_id, String name, String description, Double price, Double discount, String image, Boolean isTrending) {
        this.foodShop_id = foodShop_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.isTrending = isTrending;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getFoodShop_id() {
        return foodShop_id;
    }

    public void setFoodShop_id(Long foodShop_id) {
        this.foodShop_id = foodShop_id;
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
}
