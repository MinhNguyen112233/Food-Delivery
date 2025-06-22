package com.example.DATN.repositories;

import com.example.DATN.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    List<CartItem> findCartItemByCartId(Long cartId);
    Optional<CartItem> findById(Long id);
    Optional<CartItem> findByCartIdAndFoodId(Long cartId, Long foodId);
}
