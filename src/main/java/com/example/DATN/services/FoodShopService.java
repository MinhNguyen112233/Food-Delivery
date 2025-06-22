package com.example.DATN.services;

import com.example.DATN.dto.req.FoodShopRequest;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.repositories.FoodShopRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FoodShopService {
    FoodShopResponse getFoodShopById(Long id);
    List<FoodShopResponse> getFoodShopSearch(String search , Long categoryId);
    List<FoodShopResponse> getFoodShops();
    List<FoodShopResponse> getFoodShopsByCategoryItemId(Long categoryItemId);
    List<FoodShopResponse> get5FoodShops();
    List<FoodShopResponse> getFoodShopsTopRating();
    FoodShopResponse addFoodShop(FoodShopRequest foodShopRequest);
    FoodShopResponse updateFoodShop(Long id, FoodShopRequest foodShopRequest);
    void deleteFoodShop(Long id);
    FoodShopResponse getFoodShopByUserId(Long userId);
    List<FoodShopResponse> getFoodShopsByCategoryId(Long categoryId);
}
