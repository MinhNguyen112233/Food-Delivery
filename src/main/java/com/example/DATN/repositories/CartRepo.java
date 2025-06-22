package com.example.DATN.repositories;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.foodShop.id = :foodShopId")
    Cart findByUserIdAndFoodShopId(@Param("userId") Long userId, @Param("foodShopId") Long foodShopId);
}
