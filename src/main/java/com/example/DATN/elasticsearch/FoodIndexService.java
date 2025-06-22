//package com.example.DATN.elasticsearch;
//
//import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
//import co.elastic.clients.elasticsearch._types.query_dsl.Query;
//import co.elastic.clients.elasticsearch.core.SearchRequest;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.JsonData;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class FoodIndexService {
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//
//    public List<FoodDocument> searchFoods(String keyword, Long categoryId, Double minPrice, Double maxPrice) throws IOException {
//        // Xây dựng truy vấn tìm kiếm
//        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
//
//        if (keyword != null && !keyword.isEmpty()) {
//            boolQueryBuilder.must(m ->
//                    m.match(t -> t.field("name").query(keyword).fuzziness("AUTO"))
//            );
//        }
//
//        if (categoryId != null) {
//            boolQueryBuilder.filter(f ->
//                    f.term(t -> t.field("categoryId").value(categoryId))
//            );
//        }
//
//        Query query = Query.of(q -> q.bool(boolQueryBuilder.build()));
//
//        SearchRequest searchRequest = SearchRequest.of(s -> s
//                .index("food1")
//                .query(query)
//        );
//
//        // Thực hiện truy vấn tìm kiếm
//        SearchResponse<FoodDocument> searchResponse = elasticsearchClient.search(searchRequest, FoodDocument.class);
//
//        // Xử lý kết quả
//        List<FoodDocument> results = new ArrayList<>();
//        for (Hit<FoodDocument> hit : searchResponse.hits().hits()) {
//            if (hit.source() != null) {
//                results.add(hit.source());
//            }
//        }
//
//        return results;
//    }
//
//    public List<FoodDocument> getAllFoods() throws IOException {
//        // Tạo truy vấn match_all để lấy tất cả dữ liệu
//        Query query = Query.of(q -> q.matchAll(m -> m));
//
//        SearchRequest searchRequest = SearchRequest.of(s -> s
//                .index("food1")
//                .query(query)
//                .size(10000) // Giới hạn số lượng kết quả, có thể điều chỉnh theo nhu cầu
//        );
//
//        // Thực hiện truy vấn
//        SearchResponse<FoodDocument> searchResponse = elasticsearchClient.search(searchRequest, FoodDocument.class);
//
//        // Xử lý kết quả
//        List<FoodDocument> results = new ArrayList<>();
//        for (Hit<FoodDocument> hit : searchResponse.hits().hits()) {
//            if (hit.source() != null) {
//                results.add(hit.source());
//            }
//        }
//
//        return results;
//    }
//}