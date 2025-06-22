package com.example.DATN.mapper;

import com.example.DATN.dto.req.ReviewsRequest;
import com.example.DATN.dto.res.ReviewsResponse;
import com.example.DATN.entities.Reviews;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.repositories.OrderDetailsRepo;
import com.example.DATN.repositories.OrderRepo;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewsMapper {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValidationService validationService;


    public ReviewsResponse ReviewsToReviewsResponse(Reviews reviews) {
        ReviewsResponse response = new ReviewsResponse();
        validationService.validateReviewId(reviews.getId());
        validationService.validateOrderDetailId(reviews.getOrderDetails().getId());
        response.setId(reviews.getId());
        validationService.validateFoodId(reviews.getFood().getId());
        validationService.validateUserId(reviews.getUser().getId());
        response.setOrder_detail_id(reviews.getOrderDetails().getId());
        response.setUser(userMapper.UserToUserResponse(reviews.getUser())); // Assuming you have a method to convert User to UserResponse
        response.setFood(foodMapper.FoodToFoodResponse(reviews.getFood())); // Assuming you have a method to convert Food to FoodResponse
        response.setRating(reviews.getRating());
        response.setComment(reviews.getComment());
        return response;
    }

    public Reviews ReviewsRequestToReviews(ReviewsRequest request) {
        Reviews reviews = new Reviews();
        validationService.validateUserId(request.getUser_id());
        validationService.validateFoodId(request.getFood_id());
        reviews.setUser(userRepo.findById(request.getUser_id()).orElseThrow(() -> new RuntimeException("User Not Found")));
        reviews.setFood(foodRepo.findById(request.getFood_id()).orElseThrow(() -> new RuntimeException("Food Not Found")));
        reviews.setOrderDetails(orderDetailsRepo.findById(request.getOrder_detail_id()).orElseThrow(() -> new RuntimeException("Order Not Found")));
        reviews.setRating(request.getRating());
        reviews.setComment(request.getComment());
        return reviews;
    }
}
