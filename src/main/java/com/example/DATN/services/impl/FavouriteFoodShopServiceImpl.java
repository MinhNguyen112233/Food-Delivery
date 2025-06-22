package com.example.DATN.services.impl;

import com.example.DATN.dto.req.FavouriteFoodShopRequest;
import com.example.DATN.dto.res.FavouriteFoodShopResponse;
import com.example.DATN.entities.FavouriteFoodShop;
import com.example.DATN.mapper.FavouriteFoodShopMapper;
import com.example.DATN.repositories.FavouriteFoodShopRepo;
import com.example.DATN.services.FavouriteFoodShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteFoodShopServiceImpl implements FavouriteFoodShopService {

    @Autowired
    private FavouriteFoodShopRepo favouriteFoodShopRepo;

    @Autowired
    private FavouriteFoodShopMapper favouriteFoodShopMapper;

    @Override
    public FavouriteFoodShop addFoodShop(FavouriteFoodShopRequest request) {
        // Map the request DTO to the FavouriteFoodShop entity
        FavouriteFoodShop favouriteFoodShop = favouriteFoodShopMapper.FavouriteFoodShopRequestToFavouriteFoodShop(request);

        // Save the FavouriteFoodShop entity to the database
        FavouriteFoodShop savedFoodShop = favouriteFoodShopRepo.save(favouriteFoodShop);

        // Map the saved entity to the response DTO
        return savedFoodShop;
    }

    @Override
    public void deleteFoodShop(Long foodShopId, Long userId) {
        favouriteFoodShopRepo.deleteByFoodShopIdAndUserId(foodShopId, userId);
    }


    @Override
    public List<FavouriteFoodShopResponse> getFoodShops(Long userId) {
        List<FavouriteFoodShop> entities = favouriteFoodShopRepo.findByUserId(userId);
        return entities.stream().map(favouriteFoodShopMapper::FavouriteFoodShopToFavouriteFoodShopResponse).collect(Collectors.toList());
    }

    @Override
    public FavouriteFoodShop getFavouriteFoodShop(Long foodShopId, Long userId) {
        return favouriteFoodShopRepo.findByUserIdAndFoodShopId(foodShopId, userId);
    }
}

