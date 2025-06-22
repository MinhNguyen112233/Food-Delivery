package com.example.DATN.dto.res;

import com.example.DATN.entities.User;
import jakarta.persistence.*;

public class AddressResponse {

    private Long id;

    private UserResponse user;

    private String fullName;

    private String phone;

    private String address;

    private Double latitude;

    private Double longitude;

    private int isChoose;

    public AddressResponse() {
    }

    public AddressResponse(Long id, UserResponse user, String fullName, String phone, String address, int isChoose) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.isChoose = isChoose;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getChoose() {
        return isChoose;
    }

    public void setChoose(int choose) {
        isChoose = choose;
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
