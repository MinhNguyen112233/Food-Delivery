//package com.example.DATN.elasticsearch;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.mapping.Property;
//import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
//import com.example.DATN.entities.Food;
//import com.example.DATN.repositories.FoodRepo;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FoodSyncService {
//
//    @Autowired
//    private FoodRepo foodRepository;  // Lấy dữ liệu từ MySQL
//
//    @Autowired
//    private FoodSearchRepository foodDocumentRepository; // Lưu dữ liệu vào Elasticsearch
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;  // Sử dụng ElasticsearchClient
//
//    @PostConstruct
//    public void init() {
//        try {
//            createIndexIfNotExists();
//            syncAllFoods();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void createIndexIfNotExists() throws IOException {
//        boolean indexExists = elasticsearchClient.indices().exists(e -> e.index("food")).value();
//        if (!indexExists) {
//            elasticsearchClient.indices().create(CreateIndexRequest.of(c -> c
//                    .index("food1")
//                    .mappings(m -> m
//                            .properties("id", Property.of(p -> p.keyword(k -> k)))
//                            .properties("name", Property.of(p -> p.text(t -> t)))
//                            .properties("description", Property.of(p -> p.text(t -> t)))
//                            .properties("price", Property.of(p -> p.double_(d -> d)))
//                            .properties("discount", Property.of(p -> p.double_(d -> d)))
//                            .properties("rating", Property.of(p -> p.double_(d -> d)))
//                            .properties("totalReviews", Property.of(p -> p.integer(i -> i)))
//                            .properties("image", Property.of(p -> p.text(t -> t)))
//                            .properties("isTrending", Property.of(p -> p.boolean_(b -> b)))
//                            .properties("categoryId", Property.of(p -> p.long_(l -> l)))
//                    )
//            ));
//        }
//    }
//
//    /**
//     * Đồng bộ toàn bộ dữ liệu từ MySQL sang Elasticsearch
//     */
//    public void syncAllFoods() {
//        List<Food> foodList = foodRepository.findAll(); // Lấy toàn bộ dữ liệu từ MySQL
//
//        List<FoodDocument> foodDocuments = foodList.stream()
//                .map(this::mapToFoodDocument) // Chuyển đổi sang FoodDocument
//                .collect(Collectors.toList());
//
//        foodDocumentRepository.saveAll(foodDocuments); // Lưu vào Elasticsearch
//    }
//
//    /**
//     * Chuyển đổi từ Food (MySQL) sang FoodDocument (Elasticsearch)
//     */
//    private FoodDocument mapToFoodDocument(Food food) {
//        FoodDocument document = new FoodDocument();
//        document.setId(food.getId().toString());
//        document.setName(food.getName());
//        document.setDescription(food.getDescription());
//        document.setPrice(food.getPrice());
//        document.setDiscount(food.getDiscount());
//        document.setRating(food.getRating());
//        document.setTotalReviews(food.getTotalReviews());
//        document.setImage(food.getImage());
//        document.setTrending(food.getTrending());
//        document.setCategoryId(food.getFoodShop().getCategoryItem().getCategory().getId());
//        return document;
//    }
//}
//
