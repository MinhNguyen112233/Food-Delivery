package com.example.DATN.services.impl;

import com.example.DATN.dto.req.ReviewsRequest;
import com.example.DATN.dto.res.ReviewsResponse;
import com.example.DATN.entities.Reviews;
import com.example.DATN.mapper.ReviewsMapper;
import com.example.DATN.repositories.ReviewsRepo;
import com.example.DATN.services.ReviewsService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private ReviewsRepo reviewsRepo;

    @Autowired
    private ValidationService validationService;

    @Override
    public ReviewsResponse getReviewsById(Long id) {
        validationService.validateReviewId(id);
        Reviews reviews = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Reviews Not Found"));
        return reviewsMapper.ReviewsToReviewsResponse(reviews);
    }

    @Override
    public List<ReviewsResponse> getAllReviews() {
        List<Reviews> reviews = reviewsRepo.findAll();
        return reviews.stream()
                .map(reviewsMapper::ReviewsToReviewsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewsResponse> getReviewsByFoodId(Long foodId) {
        validationService.validateFoodId(foodId);
        List<Reviews> reviews = reviewsRepo.getReviewsByFoodId(foodId);
        return reviews.stream().map(reviewsMapper :: ReviewsToReviewsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewsResponse addReviews(ReviewsRequest request) {
        Reviews reviews = reviewsMapper.ReviewsRequestToReviews(request);
        Reviews savedReviews = reviewsRepo.save(reviews);
        return reviewsMapper.ReviewsToReviewsResponse(savedReviews);
    }

    @Override
    public ReviewsResponse updateReviews(Long id, ReviewsRequest request) {
        validationService.validateReviewId(id);
        Reviews reviews = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Reviews Not Found"));
        reviews = reviewsMapper.ReviewsRequestToReviews(request);
        reviews.setId(id);
        Reviews savedReviews = reviewsRepo.save(reviews);
        return reviewsMapper.ReviewsToReviewsResponse(savedReviews);
    }

    @Override
    public void deleteReviews(Long id) {
        reviewsRepo.deleteById(id);
        Reviews reviews = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Reviews Not Found"));
        reviewsRepo.delete(reviews);
    }

    @Override
    public List<ReviewsResponse> getReviewsByOrderDetailId(Long orderDetailsId) {
        validationService.validateOrderDetailId(orderDetailsId);
        List<Reviews> reviews = reviewsRepo.getReviewsByOrderDetailsId(orderDetailsId);
        return reviews.stream().map(reviewsMapper :: ReviewsToReviewsResponse).collect(Collectors.toList());
    }
}
