package com.example.DATN.dto.req;

public class GoogleOAuthRequest {
    private String email;
    private String fullName;
    private String avatarUrl;

    // Constructors
    public GoogleOAuthRequest() {}

    public GoogleOAuthRequest(String email, String fullName, String avatarUrl) {
        this.email = email;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
