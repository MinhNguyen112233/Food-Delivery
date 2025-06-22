package com.example.DATN.services.impl;

import com.example.DATN.exceptions.ValidationError;
import com.example.DATN.exceptions.ValidationException;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OtpService {

    private final Map<String, String> otpMap = new HashMap<>();
    private final Map<String, LocalDateTime> otpExpiryMap = new HashMap<>();
    private final int EXPIRE_MINUTES = 5;

    @Autowired
    private ValidationService validationService;

    public String generateOtp(String email) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validateEmail(email , errors);
        if(!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ",errors);
        }
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpMap.put(email, otp);
        otpExpiryMap.put(email, LocalDateTime.now().plusMinutes(EXPIRE_MINUTES));
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        List<ValidationError> errors = new ArrayList<>();
        validationService.validateEmail(email , errors);
        if(!errors.isEmpty()) {
            throw new ValidationException("Thông tin không hợp lệ",errors);
        }

        String storedOtp = otpMap.get(email);
        LocalDateTime expiryTime = otpExpiryMap.get(email);

        if (storedOtp == null || expiryTime == null) return false;

        if (LocalDateTime.now().isAfter(expiryTime)) {
            otpMap.remove(email);
            otpExpiryMap.remove(email);
            return false;
        }

        return storedOtp.equals(otp);
    }
}

