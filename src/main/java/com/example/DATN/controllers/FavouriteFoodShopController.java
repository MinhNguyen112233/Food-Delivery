package com.example.DATN.controllers;

import com.example.DATN.dto.req.FavouriteFoodShopRequest;
import com.example.DATN.dto.res.FavouriteFoodShopResponse;
import com.example.DATN.entities.FavouriteFoodShop;
import com.example.DATN.services.FavouriteFoodShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouriteFoodShopController {

    @Autowired
    private FavouriteFoodShopService favouriteFoodShopService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<FavouriteFoodShop> addFoodShop(@RequestBody FavouriteFoodShopRequest request) {
        FavouriteFoodShop response = favouriteFoodShopService.addFoodShop(request);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFoodShop(@RequestParam Long foodShopId, @RequestParam Long userId) {
        favouriteFoodShopService.deleteFoodShop(foodShopId, userId);
        return ResponseEntity.ok("Food shop deleted successfully!");
    }


    @GetMapping("/list")
    public ResponseEntity<List<FavouriteFoodShopResponse>> getFoodShops(@RequestParam Long userId) {
        List<FavouriteFoodShopResponse> responses = favouriteFoodShopService.getFoodShops(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get_favourite_food_shop")
    public ResponseEntity<Object> getFoodShop(@RequestParam Long foodShopId, @RequestParam Long userId) {
        FavouriteFoodShop shop = favouriteFoodShopService.getFavouriteFoodShop(foodShopId, userId);
            return ResponseEntity.ok(shop);
    }
}

