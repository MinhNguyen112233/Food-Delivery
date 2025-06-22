package com.example.DATN.mapper;

import com.example.DATN.dto.req.FavouriteFoodShopRequest;
import com.example.DATN.dto.res.FavouriteFoodShopResponse;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.entities.FavouriteFoodShop;
import com.example.DATN.entities.FoodShop;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FavouriteFoodShopMapper {

    @Autowired
    private FoodShopMapper foodShopMapper;

    @Autowired
    private FoodShopRepo foodShopRepo;

    @Autowired
    private UserRepo userRepo;

    public FavouriteFoodShopResponse FavouriteFoodShopToFavouriteFoodShopResponse(FavouriteFoodShop foodShop) {
        FavouriteFoodShopResponse response = new FavouriteFoodShopResponse();
        response.setId(foodShop.getId());
        response.setUserId(foodShop.getUser().getId());
        FoodShopResponse foodShopResponse = foodShopMapper.FoodShopToFoodShopResponse(foodShopRepo.findById(foodShop.getFoodShopId()).orElseThrow());
        response.setFoodShopResponse(foodShopResponse);
        return response;
    }

    public FavouriteFoodShop FavouriteFoodShopRequestToFavouriteFoodShop(FavouriteFoodShopRequest request) {
        FavouriteFoodShop foodShop = new FavouriteFoodShop();
        foodShop.setUser(userRepo.findById(request.getUserId()).orElseThrow());
        foodShop.setFoodShopId(request.getFoodShopId());
        return foodShop;
    }
}
