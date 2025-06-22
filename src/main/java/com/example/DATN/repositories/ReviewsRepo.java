package com.example.DATN.repositories;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepo extends JpaRepository<Reviews, Long> {
    List<Reviews> getReviewsByFoodId(Long id);

    List<Reviews> getReviewsByOrderDetailsId(Long orderDetailsId);
}
