package com.example.DATN.controllers;

import com.example.DATN.dto.req.CategoryItemRequest;
import com.example.DATN.dto.res.CategoryItemResponse;
import com.example.DATN.services.CategoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-items")
public class CategoryItemController {

    @Autowired
    private CategoryItemService categoryItemService;

    @PostMapping
    public ResponseEntity<CategoryItemResponse> createCategoryItem(@RequestBody CategoryItemRequest categoryItemRequest) {
        CategoryItemResponse createdCategoryItem = categoryItemService.createCategoryItem(categoryItemRequest);
        return new ResponseEntity<>(createdCategoryItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryItemResponse> updateCategoryItem(
            @PathVariable Long id,
            @RequestBody CategoryItemRequest categoryItemRequest) {
        CategoryItemResponse updatedCategoryItem = categoryItemService.updateCategoryItem(id, categoryItemRequest);
        return ResponseEntity.ok(updatedCategoryItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryItem(@PathVariable Long id) {
        categoryItemService.deleteCategoryItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryItemResponse> getCategoryItemById(@PathVariable Long id) {
        CategoryItemResponse categoryItemResponse = categoryItemService.getCategoryItemById(id);
        return ResponseEntity.ok(categoryItemResponse);
    }

    @GetMapping("/get-all-category-item")
    public ResponseEntity<List<CategoryItemResponse>> getAllCategoryItems() {
        List<CategoryItemResponse> categoryItems = categoryItemService.getAllCategoryItems();
        return ResponseEntity.ok(categoryItems);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryItemResponse>> searchCategoryItemsByName(@RequestParam String name) {
        List<CategoryItemResponse> categoryItems = categoryItemService.searchCategoryItemsByName(name);
        return ResponseEntity.ok(categoryItems);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryItemResponse>> getCategoryItemsByCategoryId(@PathVariable Long categoryId) {
        List<CategoryItemResponse> categoryItems = categoryItemService.getCategoryItemsByCategoryId(categoryId);
        return ResponseEntity.ok(categoryItems);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<CategoryItemResponse>> getPaginatedCategoryItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CategoryItemResponse> categoryItems = categoryItemService.getPaginatedCategoryItems(page, size);
        return ResponseEntity.ok(categoryItems);
    }
}