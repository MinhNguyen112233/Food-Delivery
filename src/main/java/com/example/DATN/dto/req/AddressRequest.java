package com.example.DATN.dto.req;

import com.example.DATN.entities.User;
import jakarta.persistence.*;

public class AddressRequest {

    private Long user_id;

    private String fullName;

    private String phone;

    private String address;

    private Double latitude;

    private Double longitude;

    private int isChoose;

    public AddressRequest(Long user_id, String fullName, String phone, String address, int isChoose) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.isChoose = isChoose;
    }

    public AddressRequest() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
