package com.example.DATN.services.impl;

import com.example.DATN.dto.req.ChangePasswordRequest;
import com.example.DATN.dto.req.UpdateUserRequest;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import com.example.DATN.exceptions.ValidationError;
import com.example.DATN.exceptions.ValidationException;
import com.example.DATN.mapper.UserMapper;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.UserService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper:: UserToUserResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {

        validationService.validateUserId(id);

        Optional<User> userOptional = userRepo.findById(id);

        return userOptional.map(userMapper:: UserToUserResponse).orElse(null);
    }


    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        List<ValidationError> errors = new ArrayList<>();

        // Validate email or phone number if being updated
        switch (request.getField()) {
            case "email":
                if (!request.getValue().equals(user.getEmail())) {
                    validationService.validateExistingEmail(request.getValue(), errors);
                    validationService.validateEmail(request.getValue(), errors);
                }
                break;
            case "phoneNumber":
                if (!request.getValue().equals(user.getPhoneNumber())) {
                    validationService.validateExistingPhoneNumber(request.getValue(), errors);
                    validationService.validatePhoneNumber(request.getValue(), errors);
                }
                break;
        }


        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors);
        }

        // Cập nhật đúng trường cần thay đổi
        switch (request.getField()) {
            case "email": user.setEmail(request.getValue()); break;
            case "fullName": user.setFullName(request.getValue()); break;
            case "phoneNumber": user.setPhoneNumber(request.getValue()); break;
            case "avartar": user.setAvartar(request.getValue()); break;
            case "gender": user.setGender(request.getValue()); break;
            case "birthday": user.setBirthday(request.getValue()); break;
            case "job": user.setJob(request.getValue()); break;
            default: throw new RuntimeException("Invalid field");
        }

        User savedUser = userRepo.save(user);
        return userMapper.UserToUserResponse(savedUser);
    }


    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        Optional<User> userOptional = userRepo.findByEmail(email); // ✅ Đúng kiểu dữ liệu
        if (userOptional.isPresent()) { // ✅ Đúng method
            return Optional.of(userMapper.UserToUserResponse(userOptional.get())); // ✅ Đúng cú pháp
        }
        return Optional.empty();
    }

    @Override
    public UserResponse updateNewPassword(String email, String newPassword, String confirmPassword) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validateEmail(email, errors);
        validationService.validatePassword(newPassword, errors);
        validationService.validatePassword(confirmPassword, errors);
        validationService.validateSamePassword(newPassword, confirmPassword, errors);

        if(!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = userRepo.findByEmail(email).orElseThrow();
        if (user == null) {
            throw new ValidationException("Người dùng không tồn tại", errors);
        }

            user.setPassword(passwordEncoder.encode(newPassword));
            User saveUser = userRepo.save(user);
            return userMapper.UserToUserResponse(saveUser);
    }

    @Override
    public void deleteUserById(Long id) {
        validationService.validateUserId(id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    @Override
    public UserResponse changePassword(Long id, ChangePasswordRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        // Kiểm tra mật khẩu hiện tại hợp lệ
        validationService.validatePassword(request.getPassword(), errors);

        // Kiểm tra mật khẩu mới hợp lệ
        validationService.validatePassword(request.getNewPassword1(), errors);
        validationService.validatePassword(request.getNewPassword2(), errors);

        // Kiểm tra xem mật khẩu mới có khớp nhau không
        if (!request.getNewPassword1().equals(request.getNewPassword2())) {
            errors.add(new ValidationError("password", "Password Not Same" , "Mật khẩu xác nhận không khớp"));
        }

        // Nếu có lỗi, ném ngoại lệ
        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        // Tìm người dùng theo email
        Optional<User> user = userRepo.findById(id);

        if (user == null) {
            throw new ValidationException("Người dùng không tồn tại" , errors);
        }



        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new ValidationException("Mật khẩu hiện tại không chính xác", errors);
        }

        // Cập nhật mật khẩu mới
        user.get().setPassword(passwordEncoder.encode(request.getNewPassword1()));
        userRepo.save(user.get());

        // Trả về thông tin người dùng sau khi cập nhật
        return userMapper.UserToUserResponse(user.get());
    }

    @Override
    public Optional<UserResponse> getUserByPhoneNumber(String phone) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validatePhoneNumber(phone, errors);
        if(!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }
        Optional<User> userOptional = userRepo.findByPhoneNumber(phone);
        if(userOptional.isPresent()){
            return Optional.of(userMapper.UserToUserResponse(userOptional.get()));
        }
        return Optional.empty();
    }



}
