package com.example.DATN.mapper;

import com.example.DATN.dto.req.FoodRequest;
import com.example.DATN.dto.res.FoodResponse;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.Food;
import com.example.DATN.repositories.CategoryRepo;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    @Autowired
    private  FoodShopMapper foodShopMapper;
    @Autowired
    private  FoodShopRepo foodShopRepo;
    @Autowired
    private  CategoryRepo categoryRepo;
    @Autowired
    private  CategoryMapper categoryMapper;
    @Autowired
    private ValidationService validationService;


    public FoodResponse FoodToFoodResponse(Food food) {
        FoodResponse foodResponse = new FoodResponse();
        validationService.validateFoodId(food.getId());
        foodResponse.setId(food.getId());
        foodResponse.setFoodShop(foodShopMapper.FoodShopToFoodShopResponse(food.getFoodShop()));
        foodResponse.setName(food.getName());
        foodResponse.setDescription(food.getDescription());
        foodResponse.setPrice(food.getPrice());
        foodResponse.setDiscount(food.getDiscount());
        foodResponse.setRating(food.getRating());
        foodResponse.setTotalReviews(food.getTotalReviews());
        foodResponse.setImage(food.getImage());
        foodResponse.setTotalSell(food.getTotalSell());
        foodResponse.setTrending(food.getTrending());

        return foodResponse;
    }

    public Food FoodRequestToFood(FoodRequest foodRequest) {
        Food food = new Food();
        validationService.validateFoodShopId(foodRequest.getFoodShop_id());
        validationService.validateCategoryId(foodRequest.getCategory_id());
        food.setFoodShop(foodShopRepo.findById(foodRequest.getFoodShop_id()).orElseThrow());
        food.setName(foodRequest.getName());
        food.setDescription(foodRequest.getDescription());
        food.setPrice(foodRequest.getPrice());
        food.setDiscount(foodRequest.getDiscount());
        food.setImage(foodRequest.getImage());
        food.setTrending(foodRequest.getTrending());
        return food;
    }

}
