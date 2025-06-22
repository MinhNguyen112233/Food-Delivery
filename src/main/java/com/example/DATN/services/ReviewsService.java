package com.example.DATN.services;

import com.example.DATN.dto.req.ReviewsRequest;
import com.example.DATN.dto.res.ReviewsResponse;

import java.util.List;

public interface ReviewsService {
    ReviewsResponse getReviewsById(Long id);
    List<ReviewsResponse> getAllReviews();
    List<ReviewsResponse> getReviewsByFoodId(Long foodId);
    ReviewsResponse addReviews(ReviewsRequest request);
    ReviewsResponse updateReviews(Long id, ReviewsRequest request);
    void deleteReviews(Long id);

    List<ReviewsResponse> getReviewsByOrderDetailId(Long orderDetailId);
}
