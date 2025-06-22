package com.example.DATN.services.impl;

import com.example.DATN.dto.req.FoodShopRequest;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.FoodShop;
import com.example.DATN.mapper.FoodShopMapper;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.services.FoodShopService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodShopServiceImpl implements FoodShopService {

    @Autowired
    private FoodShopMapper foodShopMapper;

    @Autowired
    private FoodShopRepo foodShopRepo;

    @Autowired
    private ValidationService validationService;

    @Override
    public FoodShopResponse getFoodShopById(Long id) {
        validationService.validateFoodShopId(id);
         FoodShop foodShop = foodShopRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Shop Not Found"));
        return foodShopMapper.FoodShopToFoodShopResponse(foodShop);
    }

    @Override
    public List<FoodShopResponse> getFoodShopSearch(String search, Long categoryItemId) {
        List<FoodShop> foodShops;

        if (categoryItemId == null) {
            foodShops = foodShopRepo.findByNameContaining(search);
        } else {
            foodShops = foodShopRepo.findFoodShopsBySearchAndCategory(search, categoryItemId);
        }

        return foodShops.stream()
                .map(foodShopMapper::FoodShopToFoodShopResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<FoodShopResponse> getFoodShops() {
        List<FoodShop> foodShops = foodShopRepo.findAll();
        return foodShops.stream()
                .map(foodShopMapper::FoodShopToFoodShopResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodShopResponse> getFoodShopsByCategoryItemId(Long categoryItemId) {
        List<FoodShop> foodShops = foodShopRepo.findFoodShopByCategoryItemId(categoryItemId);
        return foodShops.stream()
                .map(foodShopMapper::FoodShopToFoodShopResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodShopResponse> get5FoodShops() {
        List<FoodShop> foodShops = foodShopRepo.find5FoodShop();
        return foodShops.stream()
                .map(foodShopMapper::FoodShopToFoodShopResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<FoodShopResponse> getFoodShopsTopRating() {
        List<FoodShop> topFoodShops = foodShopRepo.findTop5ByRatingNative();
        return topFoodShops.stream().map(foodShopMapper :: FoodShopToFoodShopResponse).collect(Collectors.toList());
    }


    @Override
    public FoodShopResponse addFoodShop(FoodShopRequest foodShopRequest) {
        FoodShop foodShop = new FoodShop();
        foodShop = foodShopMapper.FoodShopRequestToFood(foodShopRequest);
        foodShop.setRating(Double.valueOf(0));
        foodShop.setTotalReviews(0);

        FoodShop saveFoodShop = foodShopRepo.save(foodShop);
        return foodShopMapper.FoodShopToFoodShopResponse(saveFoodShop);
    }

    @Override
    public FoodShopResponse updateFoodShop(Long id, FoodShopRequest foodShopRequest) {
        validationService.validateFoodShopId(id);
        FoodShop foodShop = foodShopRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Shop Not Found"));
        foodShop = foodShopMapper.FoodShopRequestToFood(foodShopRequest);
        foodShop.setId(id);
        FoodShop saveFoodShop = foodShopRepo.save(foodShop);
        return foodShopMapper.FoodShopToFoodShopResponse(saveFoodShop);
    }

    @Override
    public void deleteFoodShop(Long id) {
        validationService.validateFoodShopId(id);
        FoodShop foodShop = foodShopRepo.findById(id).orElseThrow(() -> new RuntimeException("Food Shop Not Found"));
        foodShopRepo.delete(foodShop);
    }

    @Override
    public FoodShopResponse getFoodShopByUserId(Long userId) {
        validationService.validateUserId(userId);
        FoodShop foodShop = foodShopRepo.findFirstByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Food Shop Not Found for this User"));
        return foodShopMapper.FoodShopToFoodShopResponse(foodShop);
    }


    @Override
    public List<FoodShopResponse> getFoodShopsByCategoryId(Long categoryId) {
        List<FoodShop> foodShops = foodShopRepo.findByCategoryId(categoryId);
        return foodShops.stream().map(foodShopMapper::FoodShopToFoodShopResponse).collect(Collectors.toList());
    }


}
