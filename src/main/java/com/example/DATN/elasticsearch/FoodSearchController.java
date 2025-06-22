//package com.example.DATN.elasticsearch;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/search")
//public class FoodSearchController {
//
//    @Autowired
//    private FoodIndexService foodIndexService;
//
//    @GetMapping
//    public List<FoodDocument> searchFoods(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Long categoryId,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice) throws IOException {
//        return foodIndexService.searchFoods(keyword, categoryId, minPrice, maxPrice);
//    }
//
//    @GetMapping("/all")
//    public List<FoodDocument> getAllFoods() throws IOException {
//        return foodIndexService.getAllFoods();
//    }
//
//}
//
