package com.example.DATN.mapper;

import com.example.DATN.dto.req.CategoryRequest;
import com.example.DATN.dto.res.CategoryResponse;
import com.example.DATN.entities.Category;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Autowired
    public ValidationService validationService;

    @Autowired
    public CategoryItemMapper categoryItemMapper;


    public CategoryResponse CategoryToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        validationService.validateCategoryId(category.getId());
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setImage(category.getImage());
        categoryResponse.setCategoryItemResponse(category.getCategoryItem().stream().map(categoryItemMapper::CategoryItemToCategoryItemResponse).toList());
        return categoryResponse;
    }

    public Category CategoryRequestToCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setImage(categoryRequest.getImage());
        return category;
    }
}
