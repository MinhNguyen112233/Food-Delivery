package com.example.DATN.dto.res;

import com.example.DATN.entities.CategoryItem;
import com.example.DATN.entities.Food;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class CategoryResponse {

    private Long id;

    private String name;

    private String image;

    private List<CategoryItemResponse> categoryItemResponse;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryItemResponse> getCategoryItemResponse() {
        return categoryItemResponse;
    }

    public void setCategoryItemResponse(List<CategoryItemResponse> categoryItemResponse) {
        this.categoryItemResponse = categoryItemResponse;
    }
}
