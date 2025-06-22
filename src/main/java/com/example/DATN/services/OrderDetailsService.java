package com.example.DATN.services;

import com.example.DATN.dto.req.OrderDetailsRequest;
import com.example.DATN.dto.res.OrderDetailsResponse;

import java.util.List;

public interface OrderDetailsService {
    OrderDetailsResponse getOrderDetailsById(Long id);
    List<OrderDetailsResponse> getOrderDetailsByOrderId(Long orderId);
    List<OrderDetailsResponse> getAllOrderDetails();
    OrderDetailsResponse addOrderDetails(OrderDetailsRequest request);
    OrderDetailsResponse updateOrderDetails(Long id, OrderDetailsRequest request);
    void deleteOrderDetails(Long id);
}
