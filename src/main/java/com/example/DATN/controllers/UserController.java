package com.example.DATN.controllers;

import com.example.DATN.dto.req.ChangePasswordRequest;
import com.example.DATN.dto.req.UpdateNewPasswordRequest;
import com.example.DATN.dto.req.UpdateUserRequest;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.services.FileService;
import com.example.DATN.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all_users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        Page<UserResponse> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get_user")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/update-user")
    public ResponseEntity<UserResponse> updateUser(
            @RequestParam("id") Long id,
            @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }


//    @PutMapping("/update-user-avatar")
//    public ResponseEntity<UserResponse> updateUserImage(
//            @RequestParam(value = "file", required = false) MultipartFile file,
//            @RequestParam("id") Long id,
//            @ModelAttribute UpdateUserRequest request) throws IOException {
//        String fileName = null;
//        if (file != null && !file.isEmpty()) {
//            fileName = fileService.storeFile(file);
//        }
//
//        UserResponse response = userService.updateUser(id, request);
//        return ResponseEntity.ok(response);
//    }
    //@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/update-user-avatar")
    public ResponseEntity<UserResponse> updateUserAvatar(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("id") Long id) throws IOException {

        String fileName = null;

        // Lưu file ảnh nếu có
        if (file != null && !file.isEmpty()) {
            // Kiểm tra định dạng file
            String contentType = file.getContentType();
            if (contentType == null ) {
                throw new IllegalArgumentException("File phải là ảnh");
            }

            // Lưu file và lấy tên file
            fileName = fileService.storeFile(file);
        }

        // Tạo request để cập nhật avatar
        UpdateUserRequest request = new UpdateUserRequest();
        request.setField("avartar");
        request.setValue(fileName);

        // Cập nhật user
        UserResponse response = userService.updateUser(id, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete_user")
    public ResponseEntity<Void> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by_email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok) // Nếu tìm thấy -> 200 OK với data
                .orElse(ResponseEntity.ok(new UserResponse())); // Nếu không tìm thấy -> 200 OK với object rỗng
    }

    @GetMapping("/by_phone")
    public ResponseEntity<UserResponse> getUserByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok) // Nếu tìm thấy -> 200 OK với data
                .orElse(ResponseEntity.ok(new UserResponse())); // Nếu không tìm thấy -> 200 OK với object rỗng
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/change_password")
    public ResponseEntity<UserResponse> updatePassword(@RequestParam("id") Long id, @RequestBody ChangePasswordRequest request) {
        UserResponse result = userService.changePassword(id, request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-new-password")
    public ResponseEntity<UserResponse> updateNewPassword(@RequestBody UpdateNewPasswordRequest request) {
        UserResponse result = userService.updateNewPassword(request.getEmail(), request.getPassword(), request.getConfirmPassword());
        return ResponseEntity.ok(result);
    }



}