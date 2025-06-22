package com.example.DATN.services.impl;

import com.example.DATN.dto.req.LoginRequest;
import com.example.DATN.dto.req.RegisterRequest;
import com.example.DATN.dto.res.LoginResponse;
import com.example.DATN.dto.res.UserResponse;
import com.example.DATN.entities.User;
import com.example.DATN.entities.UserType;
import com.example.DATN.exceptions.AccessDeniedException;
import com.example.DATN.exceptions.ValidationError;
import com.example.DATN.exceptions.ValidationException;
import com.example.DATN.mapper.UserMapper;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.AuthService;
import com.example.DATN.services.jwt.JwtService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    // Định nghĩa các UserType được phép truy cập mobile app
    private static final Set<UserType> ALLOWED_USER_TYPES = Set.of(
            UserType.USER,
            UserType.ADMIN  // Uncomment nếu muốn cho phép ADMIN
    );

    private static final Set<UserType> ALLOWED_OWNER_TYPES = Set.of(
            UserType.OWNER
    );

    @Override
    public LoginResponse loginUser(LoginRequest requestLogin) {
        List<ValidationError> errors = new ArrayList<>();

        if (requestLogin.getPhoneNumber() != null && !requestLogin.getPhoneNumber().isEmpty()) {
            return loginWithPhoneNumber(requestLogin, errors);
        } else {
            return loginWithEmailAndPassword(requestLogin, errors);
        }
    }

    private LoginResponse loginWithPhoneNumber(LoginRequest requestLogin, List<ValidationError> errors) {
        // Validate phone number
        validationService.validatePhoneNumber(requestLogin.getPhoneNumber(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        // Find user by phone number
        Optional<User> optionalUser = userRepo.findByPhoneNumber(requestLogin.getPhoneNumber());
        if (optionalUser.isEmpty()) {
            errors.add(new ValidationError("phoneNumber", "PhoneNumber Not Exists", "Không tìm thấy người dùng với số điện thoại này"));
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = optionalUser.get();

        // Kiểm tra UserType có được phép truy cập không
        if (!isUserTypeAllowed(user.getUserType())) {
            throw new AccessDeniedException("Tài khoản không có quyền truy cập ứng dụng này");
        }

        return generateLoginResponse(user);
    }

    private LoginResponse loginWithEmailAndPassword(LoginRequest requestLogin, List<ValidationError> errors) {
        // Validate email and password
        validationService.validate(requestLogin.getEmail(), requestLogin.getPassword(), errors);

        // Check if user exists
        Optional<User> optionalUser = userRepo.findByEmail(requestLogin.getEmail());
        if (optionalUser.isEmpty()) {
            errors.add(new ValidationError("email", "Email Not Exists", "Email chưa được đăng ký"));
        }

        // If there are validation errors, throw exception
        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = optionalUser.get();

        // Verify password
        if (!passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            errors.add(new ValidationError("password", "Wrong Password", "Mật khẩu không chính xác"));
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        // Kiểm tra UserType có được phép truy cập không
        if (!isUserTypeAllowed(user.getUserType())) {
            throw new AccessDeniedException("Tài khoản không có quyền truy cập ứng dụng này");
        }

        return generateLoginResponse(user);
    }

    @Override
    public LoginResponse loginOwner(LoginRequest requestLogin) {
        List<ValidationError> errors = new ArrayList<>();

        if (requestLogin.getPhoneNumber() != null && !requestLogin.getPhoneNumber().isEmpty()) {
            return loginOwnerWithPhoneNumber(requestLogin, errors);
        } else {
            return loginOwnerWithEmailAndPassword(requestLogin, errors);
        }
    }

    private LoginResponse loginOwnerWithPhoneNumber(LoginRequest requestLogin, List<ValidationError> errors) {
        // Validate phone number
        validationService.validatePhoneNumber(requestLogin.getPhoneNumber(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        // Find user by phone number
        Optional<User> optionalUser = userRepo.findByPhoneNumber(requestLogin.getPhoneNumber());
        if (optionalUser.isEmpty()) {
            errors.add(new ValidationError("phoneNumber", "PhoneNumber Not Exists", "Không tìm thấy người dùng với số điện thoại này"));
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = optionalUser.get();

        // Kiểm tra UserType có được phép truy cập không
        if (!isOwnerTypeAllowed(user.getUserType())) {
            throw new AccessDeniedException("Tài khoản không có quyền truy cập ứng dụng này");
        }

        return generateLoginResponse(user);
    }

    private LoginResponse loginOwnerWithEmailAndPassword(LoginRequest requestLogin, List<ValidationError> errors) {
        // Validate email and password
        validationService.validate(requestLogin.getEmail(), requestLogin.getPassword(), errors);

        // Check if user exists
        Optional<User> optionalUser = userRepo.findByEmail(requestLogin.getEmail());
        if (optionalUser.isEmpty()) {
            errors.add(new ValidationError("email", "Email Not Exists", "Email chưa được đăng ký"));
        }

        // If there are validation errors, throw exception
        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = optionalUser.get();

        // Verify password
        if (!passwordEncoder.matches(requestLogin.getPassword(), user.getPassword())) {
            errors.add(new ValidationError("password", "Wrong Password", "Mật khẩu không chính xác"));
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        // Kiểm tra UserType có được phép truy cập không
        if (!isOwnerTypeAllowed(user.getUserType())) {
            throw new AccessDeniedException("Tài khoản không có quyền truy cập ứng dụng này");
        }

        return generateLoginResponse(user);
    }

    private LoginResponse generateLoginResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user, user.getId());
        String refreshToken = jwtService.generateRefreshToken(user, user.getId());
        UserResponse userResponse = userMapper.UserToUserResponse(user);

        return new LoginResponse(accessToken, refreshToken, userResponse);
    }

    // Kiểm tra UserType có được phép truy cập mobile app không
    private boolean isUserTypeAllowed(UserType userType) {
        return ALLOWED_USER_TYPES.contains(userType);
    }

    private boolean isOwnerTypeAllowed(UserType userType) {
        return ALLOWED_OWNER_TYPES.contains(userType);
    }

    // Method để kiểm tra UserType cụ thể
    public boolean hasUserType(User user, UserType userType) {
        return user.getUserType() == userType;
    }

    // Method để lấy tên UserType dạng string
    public String getUserTypeString(User user) {
        return user.getUserType().name();
    }

    @Override
    public UserResponse registerUser(RegisterRequest requestRegister) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validateEmail(requestRegister.getEmail(), errors);
        validationService.validatePassword(requestRegister.getPassword(), errors);
        validationService.validatePassword(requestRegister.getNewPassword(), errors);
        validationService.validateExistingEmail(requestRegister.getEmail(), errors);
        validationService.validateSamePassword(requestRegister.getPassword(), requestRegister.getNewPassword(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = userMapper.UserRequestToUser(requestRegister);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.USER); // Mặc định tạo với UserType.USER

        userRepo.save(user);

        return userMapper.UserToUserResponse(user);
    }

    @Override
    public User registerUserCheck(RegisterRequest requestRegister) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validateEmail(requestRegister.getEmail(), errors);
        validationService.validatePassword(requestRegister.getPassword(), errors);
        validationService.validatePassword(requestRegister.getNewPassword(), errors);
        validationService.validateExistingEmail(requestRegister.getEmail(), errors);
        validationService.validateSamePassword(requestRegister.getPassword(), requestRegister.getNewPassword(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ", errors);
        }

        User user = userMapper.UserRequestToUser(requestRegister);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.USER);

        return user;
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (jwtService.isTokenBlacklisted(refreshToken)) {
            throw new RuntimeException("Token đã bị thu hồi!");
        }

        String username = jwtService.extractUsername(refreshToken);
        User userOptional = userRepo.findByEmail(username).orElseThrow();

        if (userOptional != null) {
            User user = userOptional;

            // Kiểm tra UserType khi refresh token
            if (!isUserTypeAllowed(user.getUserType())) {
                throw new AccessDeniedException("Tài khoản không còn quyền truy cập ứng dụng này");
            }

            return jwtService.generateAccessToken(user, user.getId());
        }
        throw new RuntimeException("Người dùng không tồn tại!");
    }

    // Thêm method này vào AuthServiceImpl class

    @Override
    public LoginResponse loginOrRegisterWithGoogle(String email, String fullName, String avatarUrl) {
        List<ValidationError> errors = new ArrayList<>();

        // Validate email
        validationService.validateEmail(email, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Email không hợp lệ", errors);
        }

        // Tìm user theo email
        Optional<User> optionalUser = userRepo.findByEmail(email);
        User user;

        if (optionalUser.isPresent()) {
            // User đã tồn tại - đăng nhập
            user = optionalUser.get();

            // Cập nhật thông tin từ Google nếu cần
            boolean needUpdate = false;
            if (fullName != null && !fullName.isEmpty() && !fullName.equals(user.getFullName())) {
                user.setFullName(fullName);
                needUpdate = true;
            }
            if (avatarUrl != null && !avatarUrl.isEmpty() && !avatarUrl.equals(user.getAvartar())) {
                user.setAvartar(avatarUrl);
                needUpdate = true;
            }

            if (needUpdate) {
                user.setUpdatedAt(LocalDateTime.now());
                userRepo.save(user);
            }
        } else {
            // User chưa tồn tại - tạo mới
            user = new User();
            user.setEmail(email);
            user.setFullName(fullName != null ? fullName : "User Google");
            user.setAvartar(avatarUrl);
            user.setUserType(UserType.USER);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            // Không cần password cho Google login
            user.setPassword(passwordEncoder.encode("GOOGLE_OAUTH_" + System.currentTimeMillis()));

            userRepo.save(user);
        }

        // Kiểm tra UserType có được phép truy cập không
        if (!isUserTypeAllowed(user.getUserType())) {
            throw new AccessDeniedException("Tài khoản không có quyền truy cập ứng dụng này");
        }

        return generateLoginResponse(user);
    }
}
