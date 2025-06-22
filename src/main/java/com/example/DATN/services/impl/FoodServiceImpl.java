package com.example.DATN.services.impl;

import com.example.DATN.constants.FcmTokenStorage;
import com.example.DATN.dto.req.FoodRequest;
import com.example.DATN.dto.res.FoodResponse;
import com.example.DATN.entities.Food;
import com.example.DATN.mapper.FoodMapper;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.services.FoodService;
import com.example.DATN.services.validation.ValidationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private ValidationService validationService;


    @Override
    public FoodResponse getFoodById(Long id) {
        validationService.validateFoodId(id);
        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Not Found"));
        return foodMapper.FoodToFoodResponse(food);
    }

    @Override
    public List<FoodResponse> getFoodByFoodShopId(Long id) {
        validationService.validateFoodShopId(id);
        List<Food> foods = foodRepo.findByFoodShopId(id);
        return foods.stream().map(foodMapper::FoodToFoodResponse).collect(Collectors.toList());
    }

    @Override
    public List<FoodResponse> getAllFoods() {
        List<Food> foods = foodRepo.findAll();
        return foods.stream()
                .map(foodMapper::FoodToFoodResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FoodResponse addFood(FoodRequest foodRequest) {
        Food food = foodMapper.FoodRequestToFood(foodRequest);
        Food savedFood = foodRepo.save(food);
        return foodMapper.FoodToFoodResponse(savedFood);
    }

    @Override
    public FoodResponse updateFood(Long id, FoodRequest foodRequest) {
        validationService.validateFoodId(id);
        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Not Found"));
        food = foodMapper.FoodRequestToFood(foodRequest);
        food.setId(id);
        Food savedFood = foodRepo.save(food);
        return foodMapper.FoodToFoodResponse(savedFood);
    }

    @Override
    public void deleteFood(Long id) {
        validationService.validateFoodId(id);
        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Not Found"));
        foodRepo.delete(food);
    }


    // Cứ 1h sẽ chạy hàm này (3600000ms = 1 giờ)
    //@Scheduled(fixedRate = 3600000)


//    @Scheduled(fixedRate = 3600000)
//    @Transactional
//    @Override
//    public void removeRandomDiscount() {
//        String fcmToken = fcmTokenStorage.getFcmToken();
//        if(fcmToken != null) {
//            // Lấy danh sách 4 món đang có giảm giá
//            List<Food> foods = foodRepo.findTop4ByDiscountGreaterThan(0.0);
//
//            if (foods.isEmpty()) {
//                //System.out.println("Không có món nào cần loại bỏ giảm giá. " + LocalDateTime.now());
//                return;
//            }
//
//            for (Food food : foods) {
//                food.setDiscount(0.0); // Xóa giảm giá
//                foodRepo.save(food);
//            }
//
//            //System.out.println("Đã loại bỏ giảm giá của 4 món ăn. " + LocalDateTime.now());
//        }
//    }
//
//    @Scheduled(fixedRate = 1800000)
//    @Transactional
//    @Override
//    public void logDiscountedFoods() {
//       String fcmToken = fcmTokenStorage.getFcmToken();
//       if(fcmToken != null) {
//           // Lấy danh sách 4 món đang có giảm giá
//           List<Food> foods = foodRepo.findTop4ByDiscountGreaterThan(0.0);
//
//           if (foods.isEmpty()) {
//               //System.out.println("Không có món ăn nào đang có giảm giá. " + LocalDateTime.now());
//               return;
//           }
//
//           System.out.println("Danh sách 4 món ăn đang có giảm giá: " + LocalDateTime.now());
//           for (Food food : foods) {
//               //System.out.println("Món: " + food.getName() + ", Giảm giá: " + food.getDiscount() + "%");
//           }
//       }
//    }

}
