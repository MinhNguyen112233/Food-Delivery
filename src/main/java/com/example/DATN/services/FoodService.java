package com.example.DATN.services;

import com.example.DATN.dto.req.FoodRequest;
import com.example.DATN.dto.res.FoodResponse;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface FoodService {
        FoodResponse getFoodById(Long id);

    List<FoodResponse> getFoodByFoodShopId(Long id);

    List<FoodResponse> getAllFoods();
        FoodResponse addFood(FoodRequest foodRequest);
        FoodResponse updateFood(Long id, FoodRequest foodRequest);
        void deleteFood(Long id);


//        @Scheduled(fixedRate = 3600000) // 1 giờ
//        @Transactional
//        void removeRandomDiscount();
//
//        @Scheduled(fixedRate = 1800000) // 30 phút = 1,800,000 ms
//        @Transactional
//        void logDiscountedFoods();
}
