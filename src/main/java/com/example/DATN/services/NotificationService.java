package com.example.DATN.services;

import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.NotificationFcm;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface NotificationService {
    void sendNotification(String token, String title, String body, String route, String id , LocalDateTime time);

    @Scheduled(fixedRate = 3600000)
    @Transactional
    void applyRandomDiscount();

    List<NotificationFcm> getNotificationFcms();

    List<FoodShopResponse> getFoodShopsByNotificationId(Long notificationId);
}
