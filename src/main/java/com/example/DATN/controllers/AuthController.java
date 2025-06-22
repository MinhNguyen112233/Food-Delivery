package com.example.DATN.controllers;

import com.example.DATN.constants.FcmTokenStorage;
import com.example.DATN.dto.req.GoogleOAuthRequest;
import com.example.DATN.dto.req.LoginRequest;
import com.example.DATN.dto.req.RegisterRequest;
import com.example.DATN.dto.res.LoginResponse;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import com.example.DATN.exceptions.ValidationException;
import com.example.DATN.services.AuthService;
//import com.example.DATN.services.jwt.JwtService;
import com.example.DATN.services.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private FcmTokenStorage fcmTokenStorage;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest requestRegister) {
        UserResponse responseUser = authService.registerUser(requestRegister);
        return ResponseEntity.ok(responseUser);
    }


    @PostMapping("/registerCheck")
    public ResponseEntity<User> registerCheck(@Valid @RequestBody RegisterRequest requestRegister) {
        User user = authService.registerUserCheck(requestRegister);
        return ResponseEntity.ok(user);
    }

//    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestLogin) {
        LoginResponse responseUser = authService.loginUser(requestLogin);
        fcmTokenStorage.setFcmToken(requestLogin.getFcmToken());
        System.out.println("Login FCM Token : " + fcmTokenStorage.getFcmToken());
        if (responseUser != null) {
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/login_owner")
    public ResponseEntity<LoginResponse> loginOwner(@Valid @RequestBody LoginRequest requestLogin) {
        LoginResponse responseUser = authService.loginOwner(requestLogin);
        if (responseUser != null) {
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Lấy thời gian hết hạn của token
        Date expirationDate = jwtService.extractExpiration(token);
        long expirationMillis = expirationDate.getTime() - System.currentTimeMillis();

        if (expirationMillis > 0) {
            jwtService.invalidateToken(token);
        }

        fcmTokenStorage.setFcmToken("");

        return ResponseEntity.ok("Đăng xuất thành công!");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            String newAccessToken = authService.refreshAccessToken(refreshToken.replace("Bearer ", ""));
            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/oauth/google")
    public ResponseEntity<LoginResponse> googleOAuth(@RequestBody GoogleOAuthRequest request) {
        try {
            LoginResponse response = authService.loginOrRegisterWithGoogle(
                    request.getEmail(),
                    request.getFullName(),
                    request.getAvatarUrl()
            );
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
