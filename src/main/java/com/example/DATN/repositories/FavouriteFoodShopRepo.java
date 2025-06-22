package com.example.DATN.repositories;

import com.example.DATN.entities.FavouriteFoodShop;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteFoodShopRepo extends JpaRepository<FavouriteFoodShop, Long> {
    List<FavouriteFoodShop> findByUserId(Long userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM FavouriteFoodShop f WHERE f.foodShopId = :foodShopId AND f.user.id = :userId")
    void deleteByFoodShopIdAndUserId(Long foodShopId, Long userId);


    @Query("SELECT f FROM FavouriteFoodShop f WHERE f.foodShopId = :foodShopId AND f.user.id = :userId")
    FavouriteFoodShop findByUserIdAndFoodShopId(Long foodShopId, Long userId);
}
