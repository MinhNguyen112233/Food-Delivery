package com.example.DATN.services;

import com.example.DATN.dto.req.CategoryItemRequest;
import com.example.DATN.dto.res.CategoryItemResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryItemService {
    // Create a new category item
    CategoryItemResponse createCategoryItem(CategoryItemRequest categoryItemRequest);

    // Update an existing category item
    CategoryItemResponse updateCategoryItem(Long id, CategoryItemRequest categoryItemRequest);

    // Delete a category item
    void deleteCategoryItem(Long id);

    // Get category item by ID
    CategoryItemResponse getCategoryItemById(Long id);

    // Get all category items
    List<CategoryItemResponse> getAllCategoryItems();

    // Search category items by name
    List<CategoryItemResponse> searchCategoryItemsByName(String name);

    // Get category items by category ID
    List<CategoryItemResponse> getCategoryItemsByCategoryId(Long categoryId);

    // Get paginated category items
    Page<CategoryItemResponse> getPaginatedCategoryItems(int page, int size);
}