package com.example.DATN.dto.res;

import com.example.DATN.entities.CategoryItem;

public class FoodShopResponse {
    private Long id;

    private String name;

    private String nameOwnerShop;

    private String description;

    private String address;

    private String addressDetail;

    private Double latitude;

    private Double longitude;

    private String phone;

    private Double rating;

    private String image;

    private Integer totalReviews;

    private Integer totalSell;

    private Boolean favourite;

    private Long userId;


    public FoodShopResponse() {
    }

    public FoodShopResponse(Long id, String name, String description, String address, String phone, Double rating, String image, Integer totalReviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.image = image;
        this.totalReviews = totalReviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getNameOwnerShop() {
        return nameOwnerShop;
    }

    public void setNameOwnerShop(String nameOwnerShop) {
        this.nameOwnerShop = nameOwnerShop;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(Integer totalSell) {
        this.totalSell = totalSell;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
