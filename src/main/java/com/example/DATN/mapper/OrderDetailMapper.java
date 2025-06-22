package com.example.DATN.mapper;

import com.example.DATN.dto.req.OrderDetailsRequest;
import com.example.DATN.dto.res.OrderDetailsResponse;
import com.example.DATN.entities.OrderDetails;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.repositories.OrderRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private OrderRepo ordersRepo;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private ValidationService validationService;

    public OrderDetailsResponse orderDetailsToOrderDetailsResponse(OrderDetails orderDetails) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        validationService.validateOrderDetailId(orderDetails.getId());
        response.setId(orderDetails.getId());
        response.setOrder(ordersMapper.OrdersToOrdersResponse(orderDetails.getOrder())); // Assuming you have a method to convert Orders to OrdersResponse
        response.setFood(foodMapper.FoodToFoodResponse(orderDetails.getFood())); // Assuming you have a method to convert Food to FoodResponse
        response.setQuantity(orderDetails.getQuantity());
        response.setPrice(orderDetails.getPrice());
        response.setNote(orderDetails.getNote());
        return response;
    }

    public OrderDetails orderDetailsRequestToOrderDetails(OrderDetailsRequest request) {
        OrderDetails orderDetails = new OrderDetails();
        validationService.validateOrderId(request.getOrder_id());
        validationService.validateFoodId(request.getFood_id());
        orderDetails.setOrder(ordersRepo.findById(request.getOrder_id()).orElseThrow(() -> new RuntimeException("Order Not Found")));
        orderDetails.setFood(foodRepo.findById(request.getFood_id()).orElseThrow(() -> new RuntimeException("Food Not Found")));
        orderDetails.setQuantity(request.getQuantity());
        orderDetails.setPrice(request.getPrice());
        orderDetails.setNote(request.getNote());
        return orderDetails;
    }
}
