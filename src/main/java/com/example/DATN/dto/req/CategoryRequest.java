package com.example.DATN.dto.req;

public class CategoryRequest {

    private String name; // Tên danh mục (VD: Đồ ăn nhanh, Hải sản, Chay...)

    private String image;

    public CategoryRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
