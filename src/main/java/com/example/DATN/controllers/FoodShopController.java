package com.example.DATN.controllers;

import com.example.DATN.dto.req.FoodShopRequest;
import com.example.DATN.dto.res.FoodShopResponse;
import com.example.DATN.services.FileService;
import com.example.DATN.services.FoodService;
import com.example.DATN.services.FoodShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/foodShops")
public class FoodShopController {

    @Autowired
    private FoodShopService foodShopService;

    @Autowired
    private FileService fileService;


    @GetMapping("/get-food-shop-by-id")
    public FoodShopResponse getFoodShopById(@RequestParam("id") Long id) {
        return foodShopService.getFoodShopById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @GetMapping("/get-food-shop-by-user-id")
    public FoodShopResponse getFoodShopByUserId(@RequestParam("user_id") Long id) {
        return foodShopService.getFoodShopByUserId(id);
    }

    @GetMapping("/all-food-shop")
    public List<FoodShopResponse> getFoodShops() {
        return foodShopService.getFoodShops();
    }

    @GetMapping("/get-5-food-shop")
    public List<FoodShopResponse> get5FoodShops() {
        return foodShopService.get5FoodShops();
    }

    @GetMapping("/top-5-food-shop")
    public List<FoodShopResponse> getTopFoodShops() {
        return foodShopService.getFoodShopsTopRating();
    }

    @GetMapping("/get-food-shop-by-category-item-id")
    public List<FoodShopResponse> getFoodShopsByCategoryItemId(@RequestParam("id") Long categoryItemId) {
        return foodShopService.getFoodShopsByCategoryItemId(categoryItemId);
    }

    @GetMapping("/get-food-shop-search")
    public List<FoodShopResponse> getFoodShopSearch(@RequestParam String search , @RequestParam(required = false) Long categoryItemId) {
        return foodShopService.getFoodShopSearch(search , categoryItemId);
    }

    @PostMapping("/add-food-shop")
    public FoodShopResponse addFoodShop(
            @RequestBody FoodShopRequest foodShopRequest){
        return foodShopService.addFoodShop(foodShopRequest);
    }

    @PutMapping("/update-food-shop")
    public FoodShopResponse updateFoodShop(@RequestParam("id") Long id, @RequestBody FoodShopRequest foodShopRequest) {
        return foodShopService.updateFoodShop(id, foodShopRequest);
    }

    @DeleteMapping("/delete-food-shop")
    public void deleteFoodShop(@RequestParam("id") Long id) {
        foodShopService.deleteFoodShop(id);
    }


    @GetMapping("/get-food-shop-by-category-id")
    public List<FoodShopResponse> getFoodShopsByCategoryId(@RequestParam("id") Long id) {
        return foodShopService.getFoodShopsByCategoryId(id);
    }
}
