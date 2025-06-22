package com.example.DATN.repositories;

import com.example.DATN.entities.Address;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
    List<Orders> findByUserIdAndStatusIn(Long userId, List<OrderStatus> statuses);
    List<Orders> findByFoodShopIdAndStatusIn(Long foodShopId , List<OrderStatus> statuses);
}
