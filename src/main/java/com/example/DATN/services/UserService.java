package com.example.DATN.services;

import com.example.DATN.dto.req.ChangePasswordRequest;
import com.example.DATN.dto.req.UpdateUserRequest;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest updateUser);
    Optional<UserResponse> getUserByEmail(String email);
    UserResponse updateNewPassword(String email, String newPassword,String confirmPassword);
    void deleteUserById(Long id);
    UserResponse changePassword(Long id ,ChangePasswordRequest request);

    Optional<UserResponse> getUserByPhoneNumber(String phone);
}
