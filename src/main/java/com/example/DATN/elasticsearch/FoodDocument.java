//package com.example.DATN.elasticsearch;
//
//import co.elastic.clients.elasticsearch._types.mapping.Property;
//import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
//import co.elastic.clients.elasticsearch.core.IndexRequest;
//import com.example.DATN.entities.Category;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import java.time.LocalDateTime;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Document(indexName = "food1") // Tạo index Elasticsearch
//public class FoodDocument {
//
//    @Id
//    private String id;
//
//    private String name;
//    private String description;
//    private Double price;
//    private Double discount;
//    private Double rating;
//    private Integer totalReviews;
//    private String image;
//    private Boolean isTrending;
//
//    private Long categoryId;
//
//    @JsonIgnore
//    private String _class;
//
//    public String get_class() {
//        return _class;
//    }
//
//    public void set_class(String _class) {
//        this._class = _class;
//    }
//
//    public FoodDocument() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(Double discount) {
//        this.discount = discount;
//    }
//
//    public Double getRating() {
//        return rating;
//    }
//
//    public void setRating(Double rating) {
//        this.rating = rating;
//    }
//
//    public Integer getTotalReviews() {
//        return totalReviews;
//    }
//
//    public void setTotalReviews(Integer totalReviews) {
//        this.totalReviews = totalReviews;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public Boolean getTrending() {
//        return isTrending;
//    }
//
//    public void setTrending(Boolean trending) {
//        isTrending = trending;
//    }
//
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//}
