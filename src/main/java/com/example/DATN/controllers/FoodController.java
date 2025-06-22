package com.example.DATN.controllers;

import com.example.DATN.dto.req.FoodRequest;
import com.example.DATN.dto.res.FoodResponse;
//import com.example.DATN.elasticsearch.FoodDocument;
//import com.example.DATN.elasticsearch.FoodSearchService;
import com.example.DATN.entities.Food;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.services.FileService;
import com.example.DATN.services.FoodService;
import com.example.DATN.services.impl.FoodSpecification;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private FileService fileService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-all-foods")
    public List<FoodResponse> getAllFoods() {
        return foodService.getAllFoods();
    }

    
    @GetMapping("/get-food-by-food-shop-id")
    public List<FoodResponse> getFoodsByFoodShopId(
            @RequestParam("id") Long id) {
        return foodService.getFoodByFoodShopId(id);
    }

    @GetMapping("/get-food-by-id")
    public FoodResponse getFoodById(@RequestParam("id") Long id) {
        return foodService.getFoodById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-food")

    public FoodResponse addFood(
            @RequestBody FoodRequest foodRequest){

        return foodService.addFood(foodRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-food")
    public FoodResponse updateFood(@RequestParam("id") Long id, @RequestBody FoodRequest foodRequest) {
        return foodService.updateFood(id, foodRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-food")
    public ResponseEntity<Void> deleteFood(@RequestParam("id") Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Food> searchFoods(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        Specification<Food> spec = FoodSpecification.filterFoods(name, categoryId, minPrice, maxPrice);
        return foodRepo.findAll(spec, Sort.by("name").ascending());
    }

    @GetMapping("/export-foods")
    public void exportFoodsToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=foods.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Food> foodList = foodRepo.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Foods");
        Row header = sheet.createRow(0);
        String[] columns = {"Id", "Name", "Description", "Rating", "TotalReviews", "Price", "FoodShop"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }
        int rowCount = 1;
        for (Food food : foodList) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(food.getId());
            row.createCell(1).setCellValue(food.getName());
            row.createCell(2).setCellValue(food.getDescription());
            row.createCell(3).setCellValue(food.getRating());
            row.createCell(4).setCellValue(food.getTotalReviews());
            row.createCell(5).setCellValue(food.getPrice());
            row.createCell(6).setCellValue(food.getFoodShop().getName());
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
