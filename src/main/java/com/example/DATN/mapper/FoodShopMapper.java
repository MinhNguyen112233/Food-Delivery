package com.example.DATN.mapper;

import com.example.DATN.dto.req.FoodShopRequest;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.Food;
import com.example.DATN.entities.FoodShop;
import com.example.DATN.repositories.CategoryItemRepo;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodShopMapper {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CategoryItemRepo categoryItemRepo;

    @Autowired
    private UserRepo userRepo;

    public FoodShopResponse FoodShopToFoodShopResponse (FoodShop foodShop) {
        FoodShopResponse foodShopResponse = new FoodShopResponse();
        validationService.validateFoodShopId(foodShop.getId());
        foodShopResponse.setId(foodShop.getId());
        foodShopResponse.setName(foodShop.getName());
        foodShopResponse.setDescription(foodShop.getDescription());
        foodShopResponse.setAddress(foodShop.getAddress());
        foodShopResponse.setPhone(foodShop.getPhone());
        foodShopResponse.setRating(foodShop.getRating());
        foodShopResponse.setImage(foodShop.getImage());
        foodShopResponse.setTotalReviews(foodShop.getTotalReviews());
        foodShopResponse.setTotalSell(foodShop.getTotalSell());
        foodShopResponse.setNameOwnerShop(foodShop.getNameOwnerShop());
        foodShopResponse.setUserId(foodShop.getUser().getId());
        foodShopResponse.setFavourite(foodShop.getFavourite());
        foodShopResponse.setAddressDetail(foodShop.getAddressDetail());
        foodShopResponse.setLatitude(foodShop.getLatitude());
        foodShopResponse.setLongitude(foodShop.getLongitude());
        validationService.validateCategoryItemId(foodShop.getCategoryItem().getId());
        return foodShopResponse;
    }
    
    public FoodShop FoodShopRequestToFood (FoodShopRequest request) {
        FoodShop foodShop = new FoodShop();
        foodShop.setName(request.getName());
        foodShop.setDescription(request.getDescription());
        foodShop.setImage(request.getImage());
        foodShop.setAddress(request.getAddress());
        foodShop.setPhone(request.getPhone());
        foodShop.setUser(userRepo.findById(request.getUserId()).orElse(null));
        foodShop.setNameOwnerShop(request.getNameOwnerShop());
        foodShop.setFavourite(request.getFavourite());
        foodShop.setAddressDetail(request.getAddressDetail());
        foodShop.setLatitude(request.getLatitude());
        foodShop.setLongitude(request.getLongitude());
        foodShop.setCategoryItem(categoryItemRepo.findById(request.getCategoryItemId()).orElse(null));
        return foodShop;
    }
}
