package com.example.DATN.services;

import com.example.DATN.dto.req.OrdersRequest;
import com.example.DATN.dto.res.OrdersResponse;
import com.example.DATN.entities.OrderStatus;

import java.util.List;

public interface    OrdersService {
    OrdersResponse getOrderById(Long id);
    List<OrdersResponse> getAllOrders();
    List<OrdersResponse> getOrdersByUserId(Long userId);

    List<OrdersResponse> getOrdersByUserIdAndStatus(Long userId, List<OrderStatus> status);

    List<OrdersResponse> getOrdersByFoodShopIdAndStatus(Long foodShopId, List<OrderStatus> status);

    OrdersResponse addOrder(Long cartId, OrdersRequest request);
    OrdersResponse updateOrder(Long id, OrdersRequest request);
    OrdersResponse updateOrderStatus(Long id, OrderStatus request);
    void deleteOrder(Long id);
}
