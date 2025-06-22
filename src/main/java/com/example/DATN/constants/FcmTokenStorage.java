package com.example.DATN.constants;

import org.springframework.stereotype.Component;

@Component
public class FcmTokenStorage {
    private String fcmToken;

    public void setFcmToken(String token) {
        this.fcmToken = token;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
