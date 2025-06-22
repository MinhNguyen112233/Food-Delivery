package com.example.DATN.mapper;

import com.example.DATN.dto.req.CategoryItemRequest;
import com.example.DATN.dto.res.CategoryItemResponse;
import com.example.DATN.entities.CategoryItem;
import com.example.DATN.repositories.CategoryRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryItemMapper {

    @Autowired
    public ValidationService validationService;

    @Autowired
    public CategoryRepo categoryRepo;


    public CategoryItemResponse CategoryItemToCategoryItemResponse(CategoryItem categoryItem) {
        CategoryItemResponse categoryItemResponse = new CategoryItemResponse();
        categoryItemResponse.setId(categoryItem.getId());
        categoryItemResponse.setName(categoryItem.getName());
        categoryItemResponse.setImage(String.valueOf(categoryItem.getImage()));
        return categoryItemResponse;
    }

    public CategoryItem CategoryItemRequestToCategoryItem(CategoryItemRequest categoryItemRequest) {
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setName(categoryItemRequest.getName());
        categoryItem.setImage(categoryItemRequest.getImage());
        validationService.validateCategoryId(categoryItemRequest.getCategoryId());
        categoryItem.setCategory(categoryRepo.findById(categoryItemRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("User Not Found")));
        return categoryItem;
    }


}
