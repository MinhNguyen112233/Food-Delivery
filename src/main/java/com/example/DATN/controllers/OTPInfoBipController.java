package com.example.otpservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class OTPInfoBipController {

    @Value("${infobip.api.url}")
    private String infobipApiUrl;

    @Value("${infobip.api.key}")
    private String infobipApiKey;

    @Value("${infobip.sender}")
    private String sender;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, Long> otpExpiry = new HashMap<>();

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String otp = generateOTP();

        otpStorage.put(phone, otp);
        otpExpiry.put(phone, System.currentTimeMillis() + (5 * 60 * 1000)); // OTP hết hạn sau 5 phút

        String message = "Your OTP code is: " + otp;
        sendSMS(phone, message);

        return ResponseEntity.ok("OTP sent to " + phone);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String otp = request.get("otp");

        if (!otpStorage.containsKey(phone)) {
            return ResponseEntity.badRequest().body("OTP expired or not sent");
        }

        if (System.currentTimeMillis() > otpExpiry.get(phone)) {
            otpStorage.remove(phone);
            otpExpiry.remove(phone);
            return ResponseEntity.badRequest().body("OTP expired");
        }

        if (otpStorage.get(phone).equals(otp)) {
            otpStorage.remove(phone);
            otpExpiry.remove(phone);
            return ResponseEntity.ok("OTP verified successfully!");
        }

        return ResponseEntity.badRequest().body("Invalid OTP");
    }

    private void sendSMS(String phone, String message) {
        Map<String, Object> payload = Map.of(
                "from", sender,
                "to", phone,
                "text", message
        );
        restTemplate.postForEntity(infobipApiUrl, payload, String.class);
    }

    private String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
