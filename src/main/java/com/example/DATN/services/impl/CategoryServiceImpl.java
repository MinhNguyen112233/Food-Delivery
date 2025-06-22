package com.example.DATN.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.DATN.dto.req.CategoryRequest;
import com.example.DATN.dto.res.CategoryResponse;
import com.example.DATN.dto.res.FoodResponse;
import com.example.DATN.entities.Category;
import com.example.DATN.entities.Food;
import com.example.DATN.mapper.CategoryMapper;
import com.example.DATN.mapper.FoodMapper;
import com.example.DATN.repositories.CategoryRepo;
import com.example.DATN.services.CategoryService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper :: CategoryToCategoryResponse).collect(Collectors.toList());
    }
    @Override
    public CategoryResponse getCategoryById(Long id) {
        validationService.validateCategoryId(id);
        return categoryMapper.CategoryToCategoryResponse(categoryRepository.findById(id).get());
    }
    @Override
    public CategoryResponse addCategory(CategoryRequest request) {
        Category category = categoryMapper.CategoryRequestToCategory(request);
        return categoryMapper.CategoryToCategoryResponse(categoryRepository.save(category));
    }
    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest newCategory) {
        validationService.validateCategoryId(id);
        Category category = categoryRepository.findById(id).get();
        category = categoryMapper.CategoryRequestToCategory(newCategory);
        category.setId(id);
        return categoryMapper.CategoryToCategoryResponse(categoryRepository.save(category));
    }
    @Override
    public void deleteCategory(Long id) {
        validationService.validateCategoryId(id);
        categoryRepository.deleteById(id);
    }

}
