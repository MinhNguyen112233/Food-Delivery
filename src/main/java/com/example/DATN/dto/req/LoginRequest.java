package com.example.DATN.dto.req;

import org.springframework.stereotype.Component;

@Component
public class LoginRequest {

    private String email;
    private String password;
    private String phoneNumber;
    private String fcmToken;

    public LoginRequest() {}

    public LoginRequest(String email, String password, String phoneNumber, String fcmToken) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.fcmToken = fcmToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
