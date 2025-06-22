package com.example.DATN.services.impl;

import com.example.DATN.dto.req.CategoryItemRequest;
import com.example.DATN.dto.res.CategoryItemResponse;
import com.example.DATN.entities.Category;
import com.example.DATN.entities.CategoryItem;
import com.example.DATN.mapper.CategoryItemMapper;
import com.example.DATN.repositories.CategoryItemRepo;
import com.example.DATN.repositories.CategoryRepo;
import com.example.DATN.services.CategoryItemService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryItemServiceImpl implements CategoryItemService {

    @Autowired
    private CategoryItemRepo categoryItemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryItemMapper categoryItemMapper;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public CategoryItemResponse createCategoryItem(CategoryItemRequest categoryItemRequest) {
        validationService.validateCategoryId(categoryItemRequest.getCategoryId());
        CategoryItem categoryItem = categoryItemMapper.CategoryItemRequestToCategoryItem(categoryItemRequest);
        CategoryItem savedCategoryItem = categoryItemRepo.save(categoryItem);
        return categoryItemMapper.CategoryItemToCategoryItemResponse(savedCategoryItem);
    }

    @Override
    @Transactional
    public CategoryItemResponse updateCategoryItem(Long id, CategoryItemRequest categoryItemRequest) {
        validationService.validateCategoryId(categoryItemRequest.getCategoryId());
        CategoryItem existingCategoryItem = categoryItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Item not found with id: " + id));

        existingCategoryItem.setName(categoryItemRequest.getName());
        existingCategoryItem.setImage(categoryItemRequest.getImage());

        if (categoryItemRequest.getCategoryId() != null) {
            Category category = categoryRepo.findById(categoryItemRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryItemRequest.getCategoryId()));
            existingCategoryItem.setCategory(category);
        }

        existingCategoryItem.setUpdatedAt(LocalDateTime.now());
        CategoryItem updatedCategoryItem = categoryItemRepo.save(existingCategoryItem);

        return categoryItemMapper.CategoryItemToCategoryItemResponse(updatedCategoryItem);
    }

    @Override
    @Transactional
    public void deleteCategoryItem(Long id) {
        validationService.validateCategoryId(id);
        if (!categoryItemRepo.existsById(id)) {
            throw new RuntimeException("Category Item not found with id: " + id);
        }
        categoryItemRepo.deleteById(id);
    }

    @Override
    public CategoryItemResponse getCategoryItemById(Long id) {
        validationService.validateCategoryId(id);
        CategoryItem categoryItem = categoryItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Item not found with id: " + id));
        return categoryItemMapper.CategoryItemToCategoryItemResponse(categoryItem);
    }

    @Override
    public List<CategoryItemResponse> getAllCategoryItems() {
        List<CategoryItem> categoryItems = categoryItemRepo.findAll();
        return categoryItems.stream()
                .map(categoryItemMapper::CategoryItemToCategoryItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryItemResponse> searchCategoryItemsByName(String name) {
        List<CategoryItem> categoryItems = categoryItemRepo.findByNameContainingIgnoreCase(name);
        return categoryItems.stream()
                .map(categoryItemMapper::CategoryItemToCategoryItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryItemResponse> getCategoryItemsByCategoryId(Long categoryId) {
        validationService.validateCategoryId(categoryId);
        List<CategoryItem> categoryItems = categoryItemRepo.findByCategoryId(categoryId);
        return categoryItems.stream()
                .map(categoryItemMapper::CategoryItemToCategoryItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryItemResponse> getPaginatedCategoryItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryItem> categoryItemPage = categoryItemRepo.findAll(pageable);

        List<CategoryItemResponse> categoryItemResponses = categoryItemPage.getContent().stream()
                .map(categoryItemMapper::CategoryItemToCategoryItemResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(
                categoryItemResponses,
                pageable,
                categoryItemPage.getTotalElements()
        );
    }
}