package com.example.DATN.controllers;


import com.example.DATN.constants.FcmTokenStorage;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.NotificationFcm;
import com.example.DATN.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FcmTokenStorage fcmTokenStorage;

    public String notification(String notification, @RequestParam String fcmToken) {
        if (notification == "a") {
            notificationService.sendNotification(fcmToken,
                    "GIẢM 50.000Đ, ăn xế thích...",
                    "⚡ Khi nhập mã SIEUTIEC50\n" +
                            "🥤 Sữa Chua Trân Châu Hạ Long\n" +
                            "🥤 Milo Dầm TUTIMI, Trà Sữa,...\n" +
                            "🧡 Đặt ngay bữa xế ngọt ngào!"
                    ,"/detailsPage", "12345", LocalDateTime.now());
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/getFcmToken")
    public String getFcmToken(@RequestParam String fcmToken) {
        fcmTokenStorage.setFcmToken(fcmToken);
        System.out.println("Get FCM Token : " + fcmTokenStorage.getFcmToken());
        return fcmToken;
    }

    @GetMapping("/getNotifications")
    public List<NotificationFcm> getNotifications() {
        return notificationService.getNotificationFcms();
    }

    @GetMapping("/get-food-shop-notificationId")
    public List<FoodShopResponse> getFoodShopsByNotificationId(@RequestParam("id") Long notificationId) {
        return notificationService.getFoodShopsByNotificationId(notificationId);
    }
}
