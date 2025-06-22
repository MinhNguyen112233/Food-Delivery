    package com.example.DATN.dto.req;
    
    public class CategoryItemRequest {
        private String name; // Tên danh mục (VD: Đồ ăn nhanh, Hải sản, Chay...)
    
        private String image;
    
        private Long categoryId;
    
        public CategoryItemRequest() {
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
    
        public Long getCategoryId() {
            return categoryId;
        }
    
        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
    }
