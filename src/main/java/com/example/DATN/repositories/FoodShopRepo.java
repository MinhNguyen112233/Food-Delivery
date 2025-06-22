package com.example.DATN.repositories;

import com.example.DATN.entities.FoodShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodShopRepo extends JpaRepository<FoodShop, Long> {
    Optional<FoodShop> findById(Long Id);

    Optional<FoodShop> findFirstByUserId(Long userId);

    @Query(value = "SELECT * FROM food_shop ORDER BY rating DESC LIMIT 5", nativeQuery = true)
    List<FoodShop> findTop5ByRatingNative();

    @Query(value = "SELECT * FROM food_shop LIMIT 5", nativeQuery = true)
    List<FoodShop> find5FoodShop();

    @Query("SELECT f FROM FoodShop f WHERE f.categoryItem.id = :categoryItemId")
    List<FoodShop> findFoodShopByCategoryItemId(@Param("categoryItemId") Long categoryItemId);

    @Query(value = "SELECT * FROM food_shop ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<FoodShop> findRandomFoodShops(@Param("limit") int limit);

//    @Query(value = "SELECT * FROM food_shop f WHERE f.name LIKE %:search% AND (:categoryId IS NULL OR f.category_item_id = :categoryId)",
//            nativeQuery = true)
//    List<FoodShop> findFoodShopsBySearchAndCategory(@Param("search") String search,
//                                                    @Param("categoryId") Long categoryId);
//
    List<FoodShop> findByNameContaining(String search);

//    @Query(value = "SELECT * FROM food_shop f WHERE f.name COLLATE utf8mb4_bin LIKE %:search% AND (:categoryId IS NULL OR f.category_item_id = :categoryId)",
//            nativeQuery = true)
//    List<FoodShop> findFoodShopsBySearchAndCategory(@Param("search") String search,
//                                                    @Param("categoryId") Long categoryId);

    @Query(value = "SELECT * FROM food_shop f WHERE f.name LIKE CONCAT('%', :search, '%') AND (:categoryId IS NULL OR f.category_item_id = :categoryId)",
            nativeQuery = true)
    List<FoodShop> findFoodShopsBySearchAndCategory(@Param("search") String search,
                                                    @Param("categoryId") Long categoryId);

    @Query("SELECT f FROM FoodShop f WHERE f.categoryItem.category.id = :categoryId")
    List<FoodShop> findByCategoryId(@Param("categoryId") Long categoryId);

}
