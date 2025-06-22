package com.example.DATN.repositories;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {
    List<Food> findTop4ByDiscountIsNullOrDiscountEquals(double v);

    List<Food> findTop4ByDiscountGreaterThan(double v);

    List<Food> findByFoodShopId(long shopId);
}
