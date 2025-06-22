package com.example.DATN.services.validation;

import com.example.DATN.entities.User;
import com.example.DATN.exceptions.ValidationException;
import com.example.DATN.exceptions.ValidationError;
import com.example.DATN.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ValidationService {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern SPECIAL_CHAR_REGEX = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
    private static final Pattern UPPERCASE_REGEX = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_REGEX = Pattern.compile(".*[a-z].*");
    private static final Pattern DIGIT_REGEX = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL_PASSWORD_REGEX = Pattern.compile(".*[!@#$%^&*()].*");

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private FoodShopRepo foodShopRepo;
    @Autowired
    private OrderDetailsRepo orderDetailRepo;
    @Autowired
    private OrderRepo ordersRepo;
    @Autowired
    private ReviewsRepo reviewsRepo;
    @Autowired
    private CategoryItemRepo categoryItemRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void validateEmail(String email, List<ValidationError> errors) {
        if (email.isEmpty()){
            errors.add(new ValidationError("email", "Email Blank", "Email đang bị bỏ trống"));
            return;
        }
        if (!email.contains(".")) {
            errors.add(new ValidationError("email", "Email Dot Invalid", "Email phải chứa dấu chấm"));
            return;
        }
        if (!email.contains("@")) {
            errors.add(new ValidationError("email", "Email @ Invalid", "Email phải chứa ký tự '@'"));
            return;
        }
        if (!email.contains(".com") && !email.contains(".net") && !email.contains(".org")) {
            errors.add(new ValidationError("email", "Email Domain Invalid", "Email phải chứa một tên miền hợp lệ"));
            return;
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            errors.add(new ValidationError("email", "Email Invalid", "Email không đúng định dạng"));
            return;
        }
        if (!SPECIAL_CHAR_REGEX.matcher(email).find()) {
            errors.add(new ValidationError("email", "Email Special Invalid", "Email phải chứa ký tự đặc biệt"));
        }
    }

    public void validatePassword(String password, List<ValidationError> errors) {
        if (password == ""){
            errors.add(new ValidationError("password", "Password Blank", "Mật khẩu đang bị bỏ trống"));
            return;
        }
        if (password.length() < 8) {
            errors.add(new ValidationError("password", "Length Password Invalid", "Mật khẩu phải dài hơn 8 ký tự"));
            return;
        }
        if (!UPPERCASE_REGEX.matcher(password).find()) {
            errors.add(new ValidationError("password", "Uppercase Password Invalid", "Mật khẩu phải có ít nhất một chữ hoa"));
            return;
        }
        if (!LOWERCASE_REGEX.matcher(password).find()) {
            errors.add(new ValidationError("password", "Lowercase Password Invalid", "Mật khẩu phải có ít nhất một chữ thường"));
            return;
        }
        if (!DIGIT_REGEX.matcher(password).find()) {
            errors.add(new ValidationError("password", "Digit Password Invalid", "Mật khẩu phải có ít nhất một chữ số"));
            return;
        }
        if (!SPECIAL_PASSWORD_REGEX.matcher(password).find()) {
            errors.add(new ValidationError("password", "Special Password Invalid", "Mật khẩu phải có ít nhất một ký tự đặc biệt"));
        }
    }

    public void validateSamePassword(String password, String newPassword, List<ValidationError> errors) {
        if (!password.equals(newPassword)) {
            errors.add(new ValidationError("password", "Same Password Invalid", "Hai mật khẩu không trùng khớp"));
        }
    }

    public void validateExistingEmail(String email , List<ValidationError> errors) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmail() != null && user.getId() != null) {
                errors.add(new ValidationError("email", "Email Exists", "Email đã được sử dụng ! Hãy sử dụng email khác"));
            }
        }
    }

    public void validateExistingPhoneNumber(String phoneNumber, List<ValidationError> errors) {
        Optional<User> optionalUser = userRepo.findByPhoneNumber(phoneNumber);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPhoneNumber() != null && user.getId() != null) {
                errors.add(new ValidationError("phoneNumber", "Phone Exists", "Số điện thoại đã được sử dụng! Hãy sử dụng số khác"));
            }
        }
    }


    public void validate(String email, String password, List<ValidationError> errors) {
        validateEmail(email, errors);
        validatePassword(password, errors);
    }

    public void validatePhoneNumber(String phoneNumber, List<ValidationError> errors) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            errors.add(new ValidationError("phoneNumber", "Phone Number Blank", "Số điện thoại không được để trống"));
            return;
        }

        if (!phoneNumber.matches("\\d+")) {
            errors.add(new ValidationError("phoneNumber", "Phone Number Format Invalid", "Số điện thoại chỉ được chứa chữ số"));
            return;
        }

        if (phoneNumber.length() != 10) {
            errors.add(new ValidationError("phoneNumber", "Phone Number Length Invalid", "Số điện thoại phải có đúng 10 chữ số"));
            return;
        }

        // Danh sách đầu số hợp lệ tại Việt Nam (đã được cập nhật sau khi chuyển đổi mã mạng)
        List<String> validPrefixes = List.of(
                "032", "033", "034", "035", "036", "037", "038", "039", // Viettel
                "070", "076", "077", "078", "079",                     // MobiFone
                "081", "082", "083", "084", "085", "086",              // VinaPhone + Viettel
                "056", "058",                                          // Vietnamobile
                "059",                                                 // Gmobile
                "087",                                                 // I-Telecom
                "088", "089", "090", "091", "092", "093", "094", "095", "096", "097", "098" // Đầu số 09
        );

        String prefix = phoneNumber.substring(0, 3);
        if (!validPrefixes.contains(prefix)) {
            errors.add(new ValidationError("phoneNumber", "Phone Number Prefix Invalid", "Đầu số điện thoại không hợp lệ ở Việt Nam"));
        }
    }


    public void validateUserId(Long userId) {
        validateEntityId(userId, userRepo, "UserId", "Người dùng không tồn tại");
    }

    public void validateAddressId(Long addressId) {
        validateEntityId(addressId, addressRepo, "AddressId", "Địa chỉ không tồn tại");
    }

    public void validateCartItemId(Long cartItemId) {
        validateEntityId(cartItemId, cartItemRepo, "CartItemId", "Mục giỏ hàng không tồn tại");
    }

    public void validateCartId(Long cartId) {
        validateEntityId(cartId, cartRepo, "CartId", "Giỏ hàng không tồn tại");
    }

    public void validateCategoryId(Long categoryId) {
        validateEntityId(categoryId, categoryRepo, "CategoryId", "Danh mục không tồn tại");
    }

    public void validateFoodId(Long foodId) {
        validateEntityId(foodId, foodRepo, "FoodId", "Món ăn không tồn tại");
    }

    public void validateFoodShopId(Long foodShopId) {
        validateEntityId(foodShopId, foodShopRepo, "FoodShopId", "Cửa hàng món ăn không tồn tại");
    }

    public void validateOrderDetailId(Long orderDetailId) {
        validateEntityId(orderDetailId, orderDetailRepo, "OrderDetailId", "Chi tiết đơn hàng không tồn tại");
    }

    public void validateOrderId(Long orderId) {
        validateEntityId(orderId, ordersRepo, "OrderId", "Đơn hàng không tồn tại");
    }

    public void validateReviewId(Long reviewId) {
        validateEntityId(reviewId, reviewsRepo, "ReviewId", "Đánh giá không tồn tại");
    }
    public void validateCategoryItemId(Long categoryItemId) {
        validateEntityId(categoryItemId , categoryItemRepo , "CategoryItemId" , "Item Danh mục không tồn tại");
    }

    private <T, R extends JpaRepository<T, Long>> void validateEntityId(Long id, R repository, String fieldName, String errorMessage) {
        List<ValidationError> errors = new ArrayList<>();
        if (id == null || id <= 0) {
            errors.add(new ValidationError(fieldName, capitalizeFirstLetter(fieldName + " Invalid"), fieldName + " không hợp lệ"));
        } else if (!repository.existsById(id)) {
            errors.add(new ValidationError(fieldName, capitalizeFirstLetter(fieldName + " Not Found"), errorMessage));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Lỗi xác thực " + fieldName, errors);
        }
    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
