package com.example.DATN.mapper;

import com.example.DATN.dto.req.LoginRequest;
import com.example.DATN.dto.req.RegisterRequest;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ValidationService validationService;

    public UserResponse UserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        validationService.validateUserId(user.getId());
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullName(user.getFullName());
        userResponse.setAvartar(user.getAvartar());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setBirthday(user.getBirthday());
        userResponse.setGender(user.getGender());
        userResponse.setJob(user.getJob());
        userResponse.setUserType(user.getUserType().toString());

        return userResponse;
    }

    public User UserRequestToUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        String email = request.getEmail();
        String fullName = email.substring(0, email.indexOf('@'));
        user.setFullName(fullName);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

}
