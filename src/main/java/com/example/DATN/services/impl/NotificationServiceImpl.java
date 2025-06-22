package com.example.DATN.services.impl;

import com.example.DATN.constants.FcmTokenStorage;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.FoodShop;
import com.example.DATN.entities.NotificationFcm;
import com.example.DATN.mapper.FoodShopMapper;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.repositories.NotificationRepo;
import com.example.DATN.services.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class  NotificationServiceImpl implements NotificationService {

    @Autowired
    private FcmTokenStorage fcmTokenStorage;

    @Autowired
    private FoodShopRepo foodShopRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private FoodShopMapper foodShopMapper;

    private final Random random = new Random();
        
    private final List<String> title = Arrays.asList(
            "🍟 Giờ ăn vặt tới rồi, mở app ngay!",
            "🍰 Bữa xế ngon hơn, ưu đãi ngập tràn!",
            "🎁 Đặt món liền tay, bất ngờ đang chờ!",
            "🍜 Ăn ngon chẳng cần đắn đo!",
            "🔥 Có gì hot hôm nay? Mở app khám phá ngay!",
            "🍱 Món ngon sẵn sàng, chỉ chờ bạn đặt!",
            "🍔 Bụng đói rồi? App lo hết!",
            "🥤 Khát rồi? Mở app chọn món liền!",
            "🍕 Pizza vừa ra lò, đặt ngay kẻo nguội!",
            "🍗 Gà rán giòn tan, ưu đãi bất ngờ!",
            "🛵 Giao hàng thần tốc, món nóng hổi liền tay!",
            "🧋 Uống gì hôm nay? Bubble tea đang chờ!",
            "🌮 Món lạ hôm nay: Khám phá vị mới!",
            "🎉 Ưu đãi mỗi ngày – Đặt món nhận quà!",
            "🕒 Đến giờ ăn rồi, đừng để bụng réo!",
            "💥 Flash sale chỉ hôm nay – Đặt món liền!",
            "😋 Món ngon đã sẵn, chỉ thiếu bạn thôi!",
            "💡 Gợi ý hôm nay: Top món hot nhất!"
    );

    private static final List<String> bodyList = Arrays.asList(
            "🥤 Sữa Chua Trân Châu Hạ Long",
            "🍹 Trà Đào Cam Sả mát lạnh",
            "🍵 Trà Sữa Trân Châu Đường Đen",
            "🍰 Bánh Flan Caramel béo mịn",
            "🍧 Kem Dừa Thái Lan thơm ngon",
            "🍕 Pizza Gà Nướng BBQ đậm vị",
            "🍜 Mì Cay Hàn Quốc thách thức",
            "🥪 Bánh Mì Chảo Thơm Lừng",
            "🍗 Gà Rán Giòn Tan",
            "🥗 Salad Rau Củ Tươi Mát",
            "🍛 Cơm Tấm Sườn Bì Chả",
            "🍣 Sushi Cá Hồi Béo Ngậy",
            "🍔 Burger Bò Mỹ Hảo Hạng",
            "🥟 Há Cảo Tôm Thịt Chấm Sốt",
            "🍩 Donut Socola Tan Chảy",
            "🥑 Sinh Tố Bơ Mát Lạnh",
            "🧋 Hồng Trà Macchiato Béo Ngậy",
            "🍛 Cơm Gà Xối Mỡ Trứng Ốp",
            "🌭 Xúc Xích Đức Nướng Than Hoa",
            "🍡 Bánh Trôi Tàu Ngọt Lịm"
    );


    public void sendNotification(String token, String title, String body, String route, String id , LocalDateTime time) {

        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setImage("https://media.istockphoto.com/id/1413772890/vi/vec-to/%C3%A1p-ph%C3%ADch-mua-s%E1%BA%AFm-flash-sale-ho%E1%BA%B7c-bi%E1%BB%83u-ng%E1%BB%AF-v%E1%BB%9Bi-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-flash-thi%E1%BA%BFt-k%E1%BA%BF-m%E1%BA%ABu-bi%E1%BB%83u-ng%E1%BB%AF-flash.jpg?s=1024x1024&w=is&k=20&c=vvCirpO9E2tOTUWezC5e6SfhtUmBTqnBKN8uyd22Md4=") // Ảnh nhỏ góc phải
                        .build())
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .putData("screen", "/discountPage")
                .putData("route", route)
                .putData("title", title)
                .putData("body", body)
                .putData("time", time.toString())
                .putData("image", "https://media.istockphoto.com/id/1413772890/vi/vec-to/%C3%A1p-ph%C3%ADch-mua-s%E1%BA%AFm-flash-sale-ho%E1%BA%B7c-bi%E1%BB%83u-ng%E1%BB%AF-v%E1%BB%9Bi-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-flash-thi%E1%BA%BFt-k%E1%BA%BF-m%E1%BA%ABu-bi%E1%BB%83u-ng%E1%BB%AF-flash.jpg?s=1024x1024&w=is&k=20&c=vvCirpO9E2tOTUWezC5e6SfhtUmBTqnBKN8uyd22Md4=")// Thêm route vào data payload
                .putData("id", id)       // Thêm id vào data payload
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 3600000)
    //@Scheduled(fixedRate = 60000) // 60,000 ms = 1 phút
    @Transactional
    @Override
    public void applyRandomDiscount() {
        String fcmToken = fcmTokenStorage.getFcmToken();
        System.out.println("Fcm Token : " + fcmToken);
        if(fcmToken != null && !fcmToken.isEmpty()) {

            List<FoodShop> foodShops = foodShopRepo.findRandomFoodShops(10);

            List<Long> foodShopIds = foodShops.stream()
                    .map(FoodShop::getId)
                    .toList();
            String foodShopIdsString = String.join(",", foodShopIds.stream()
                    .map(String::valueOf)
                    .toList());


            String titleNotification = title.get(random.nextInt(title.size()));
            Collections.shuffle(bodyList);
            String body = String.join("\n", bodyList.subList(0, 3));

            // lưu các dữ liệu cần thiết vào notificationFcm
            NotificationFcm notification = new NotificationFcm();
            notification.setTitle(titleNotification);
            notification.setBody(body);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setFoodShopIds(foodShopIdsString);
            notification.setImage("default.png");
            // lưu notificationFcm vào csdl

            NotificationFcm fcm = notificationRepo.save(notification);

            sendNotification(fcmToken, titleNotification , body , "/detailPage", fcm.getId().toString(), LocalDateTime.now());
            System.out.println("Thông báo : " + LocalDateTime.now());
        }
    }

    @Override
    public List<NotificationFcm> getNotificationFcms() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        List<NotificationFcm> notificationFcms = notificationRepo.findNotificationsFromLastWeek(startDate, endDate);
        Collections.reverse(notificationFcms);
        return notificationFcms;
    }

    @Override
    public List<FoodShopResponse> getFoodShopsByNotificationId(Long notificationId) {
        NotificationFcm notification = notificationRepo.findById(notificationId).orElseThrow();

        String foodShopIdsStr = notification.getFoodShopIds();
        if (foodShopIdsStr == null || foodShopIdsStr.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> foodShopIds = Arrays.stream(foodShopIdsStr.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Find all food shops with the extracted IDs
        List<FoodShop> foodShops = foodShopRepo.findAllById(foodShopIds);

        // Convert FoodShop entities to FoodShopResponse DTOs
        return foodShops.stream()
                .map(foodShopMapper::FoodShopToFoodShopResponse)
                .collect(Collectors.toList());
    }
}
