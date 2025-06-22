package com.example.DATN.services;

import com.example.DATN.dto.req.FavouriteFoodShopRequest;
import com.example.DATN.dto.res.FavouriteFoodShopResponse;
import com.example.DATN.entities.FavouriteFoodShop;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FavouriteFoodShopService {
    FavouriteFoodShop addFoodShop(FavouriteFoodShopRequest request);
    void deleteFoodShop(Long foodShopId, Long userId);
//    FavouriteFoodShopResponse updateFoodShop(FavouriteFoodShopRequest request);
    List<FavouriteFoodShopResponse> getFoodShops(Long userId);

    FavouriteFoodShop getFavouriteFoodShop(Long foodShopId, Long userId);
}

