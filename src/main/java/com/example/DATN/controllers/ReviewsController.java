package com.example.DATN.controllers;

import com.example.DATN.dto.req.ReviewsRequest;
import com.example.DATN.dto.res.ReviewsResponse;
import com.example.DATN.services.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-review")
    public ResponseEntity<ReviewsResponse> createReviews(@RequestBody ReviewsRequest request) {
        ReviewsResponse response = reviewsService.addReviews(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-review")
    public ResponseEntity<ReviewsResponse> updateReviews(@RequestParam("id") Long id, @RequestBody ReviewsRequest request) {
        ReviewsResponse response = reviewsService.updateReviews(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete-review")
    public ResponseEntity<Void> deleteReviews(@RequestParam("id") Long id) {
        reviewsService.deleteReviews(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/review-id")
    public ResponseEntity<ReviewsResponse> getReviewsById(@RequestParam("id") Long id) {
        ReviewsResponse response = reviewsService.getReviewsById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-reviews-by-food-id")
    public ResponseEntity<List<ReviewsResponse>> getReviewsByFoodId(@RequestParam("id") Long id) {
        List<ReviewsResponse> list = reviewsService.getReviewsByFoodId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get-reviews-by-order-detail-id")
    public ResponseEntity<List<ReviewsResponse>> getReviewsByOrderDetailId(@RequestParam("id") Long id) {
        List<ReviewsResponse> list = reviewsService.getReviewsByOrderDetailId(id);
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-all-reviews")
    public ResponseEntity<List<ReviewsResponse>> getAllReviews() {
        List<ReviewsResponse> responseList = reviewsService.getAllReviews();
        return ResponseEntity.ok(responseList);
    }
}
