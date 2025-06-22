package com.example.DATN.services;

import com.example.DATN.dto.req.CategoryRequest;
import com.example.DATN.dto.res.CategoryResponse;
import com.example.DATN.dto.res.FoodResponse;
import com.example.DATN.entities.Category;
import com.example.DATN.entities.Food;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse addCategory(CategoryRequest category);

    CategoryResponse updateCategory(Long id, CategoryRequest newCategory);

    void deleteCategory(Long id);

}
