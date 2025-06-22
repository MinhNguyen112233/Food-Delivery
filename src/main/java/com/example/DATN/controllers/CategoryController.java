package com.example.DATN.controllers;

import java.util.List;
import java.util.Optional;

import com.example.DATN.dto.req.CategoryRequest;
import com.example.DATN.dto.res.CategoryResponse;
import com.example.DATN.dto.res.FoodResponse;
import com.example.DATN.entities.Category;
import com.example.DATN.entities.Food;
import com.example.DATN.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all-categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-category-by-id")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-category")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-category")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestParam("id") Long id, @RequestBody CategoryRequest category) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-category")
    public ResponseEntity<Void> deleteCategory(@RequestParam("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
