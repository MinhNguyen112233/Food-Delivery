package com.example.DATN.services;

import com.example.DATN.dto.req.LoginRequest;
import com.example.DATN.dto.req.RegisterRequest;
import com.example.DATN.dto.res.LoginResponse;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {
    LoginResponse loginOwner(LoginRequest requestLogin);

    UserResponse registerUser(RegisterRequest requestRegister);

    User registerUserCheck(RegisterRequest requestRegister);

    LoginResponse loginUser(LoginRequest requestLogin);
    String refreshAccessToken(String refreshToken);

    LoginResponse loginOrRegisterWithGoogle(String email, String fullName, String avatarUrl);
}
