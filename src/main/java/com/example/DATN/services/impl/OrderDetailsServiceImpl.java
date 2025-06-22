package com.example.DATN.services.impl;

import com.example.DATN.dto.req.OrderDetailsRequest;
import com.example.DATN.dto.res.OrderDetailsResponse;
import com.example.DATN.entities.OrderDetails;

import com.example.DATN.mapper.OrderDetailMapper;
import com.example.DATN.repositories.OrderDetailsRepo;
import com.example.DATN.services.OrderDetailsService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailMapper orderDetailsMapper;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private ValidationService validationService;

    @Override
    public OrderDetailsResponse getOrderDetailsById(Long id) {
        validationService.validateOrderDetailId(id);
        OrderDetails orderDetails = orderDetailsRepo.findById(id).orElseThrow(() -> new RuntimeException("Order Details Not Found"));
        return orderDetailsMapper.orderDetailsToOrderDetailsResponse(orderDetails);
    }

    @Override
    public List<OrderDetailsResponse> getOrderDetailsByOrderId(Long orderId) {
        validationService.validateOrderId(orderId);
        List<OrderDetails> orderDetails = orderDetailsRepo.findByOrderId(orderId);
        return orderDetails.stream()
                .map(orderDetailsMapper::orderDetailsToOrderDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailsResponse> getAllOrderDetails() {
        List<OrderDetails> orderDetails = orderDetailsRepo.findAll();
        return orderDetails.stream()
                .map(orderDetailsMapper::orderDetailsToOrderDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse addOrderDetails(OrderDetailsRequest request) {
        OrderDetails orderDetails = orderDetailsMapper.orderDetailsRequestToOrderDetails(request);
        orderDetailsRepo.save(orderDetails);
        return orderDetailsMapper.orderDetailsToOrderDetailsResponse(orderDetails);
    }

    @Override
    public OrderDetailsResponse updateOrderDetails(Long id, OrderDetailsRequest request) {
        validationService.validateOrderDetailId(id);
        OrderDetails orderDetails = orderDetailsRepo.findById(id).orElseThrow(() -> new RuntimeException("Order Details Not Found"));
        orderDetails = orderDetailsMapper.orderDetailsRequestToOrderDetails(request);
        orderDetails.setId(id);
        orderDetailsRepo.save(orderDetails);
        return orderDetailsMapper.orderDetailsToOrderDetailsResponse(orderDetails);
    }

    @Override
    public void deleteOrderDetails(Long id) {
        validationService.validateOrderDetailId(id);
        orderDetailsRepo.deleteById(id);
    }

}