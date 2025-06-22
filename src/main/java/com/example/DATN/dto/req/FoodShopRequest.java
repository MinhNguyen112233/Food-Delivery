package com.example.DATN.dto.req;

public class FoodShopRequest {

    private String name;

    private String nameOwnerShop;

    private String description;

    private String address;

    private String addressDetail;

    private Double latitude;

    private Double longitude;

    private String phone;

    private String image;

    private Long categoryItemId;

    private Long userId;

    private Boolean favourite;

    public FoodShopRequest() {
    }

    public FoodShopRequest(String name, String nameOwnerShop, String description, String address, String addressDetail, String phone, String image, Long categoryItemId, Long userId, Boolean favourite) {
        this.name = name;
        this.nameOwnerShop = nameOwnerShop;
        this.description = description;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phone = phone;
        this.image = image;
        this.categoryItemId = categoryItemId;
        this.userId = userId;
        this.favourite = favourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOwnerShop() {
        return nameOwnerShop;
    }

    public void setNameOwnerShop(String nameOwnerShop) {
        this.nameOwnerShop = nameOwnerShop;
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

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryItemId(Long categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
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
