package com.example.DATN.services.impl;

import com.example.DATN.dto.req.OrderDetailsRequest;
import com.example.DATN.dto.req.OrdersRequest;
import com.example.DATN.dto.res.CartItemResponse;
import com.example.DATN.dto.res.OrdersResponse;
import com.example.DATN.entities.OrderDetails;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.entities.Orders;
import com.example.DATN.mapper.OrderDetailMapper;
import com.example.DATN.mapper.OrdersMapper;
import com.example.DATN.repositories.OrderDetailsRepo;
import com.example.DATN.repositories.OrderRepo;
import com.example.DATN.services.CartItemService;
import com.example.DATN.services.CartService;
import com.example.DATN.services.OrdersService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderRepo ordersRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public OrdersResponse getOrderById(Long id) {
        validationService.validateOrderId(id);
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
        return ordersMapper.OrdersToOrdersResponse(order);
    }

    @Override
    public List<OrdersResponse> getAllOrders() {
        List<Orders> orders = ordersRepo.findAll();
        return orders.stream()
                .map(ordersMapper::OrdersToOrdersResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponse> getOrdersByUserId(Long userId) {
        validationService.validateUserId(userId);
        List<Orders> orders = ordersRepo.findByUserId(userId);
        return orders.stream()
                .map(ordersMapper::OrdersToOrdersResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponse> getOrdersByUserIdAndStatus(Long userId, List<OrderStatus> status) {
        validationService.validateUserId(userId);
        List<Orders> orders = ordersRepo.findByUserIdAndStatusIn(userId, status);
        Collections.reverse(orders);
        return orders.stream()
                .map(ordersMapper::OrdersToOrdersResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponse> getOrdersByFoodShopIdAndStatus(Long foodShopId, List<OrderStatus> status) {
        validationService.validateFoodShopId(foodShopId); // Kiểm tra hợp lệ nếu cần
        List<Orders> orders = ordersRepo.findByFoodShopIdAndStatusIn(foodShopId, status);
        //Collections.reverse(orders); // Nếu muốn đảo ngược (ví dụ theo thời gian mới nhất)
        return orders.stream()
                .map(ordersMapper::OrdersToOrdersResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrdersResponse addOrder(Long cartId , OrdersRequest request) {

        Orders order = ordersMapper.OrdersRequestToOrders(request);
        Orders savedOrder = ordersRepo.save(order);
        savedOrder.setOrderCode("MFOOD " + savedOrder.getId());
        Orders updateOrder = ordersRepo.save(savedOrder);

        List<CartItemResponse> cartItems = cartItemService.getCartItemByCartId(cartId);
        for (CartItemResponse item : cartItems) {
            OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
            orderDetailsRequest.setOrder_id(savedOrder.getId());
            orderDetailsRequest.setFood_id(item.getFood().getId());
            orderDetailsRequest.setQuantity(item.getQuantity());
            BigDecimal price = BigDecimal.valueOf(item.getFood().getPrice());
            orderDetailsRequest.setPrice(price);

            OrderDetails orderDetails = orderDetailMapper.orderDetailsRequestToOrderDetails(orderDetailsRequest);
            orderDetailsRepo.save(orderDetails);
            cartItemService.deleteCartItem(item.getId());
        }
        cartService.deleteCart(cartId);
        OrdersResponse response = ordersMapper.OrdersToOrdersResponse(updateOrder);
        return response;
    }

    @Override
    public OrdersResponse updateOrder(Long id, OrdersRequest request) {
        validationService.validateOrderId(id);
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));
        order = ordersMapper.OrdersRequestToOrders(request);
        order.setId(id);
        Orders savedOrder = ordersRepo.save(order);
        return ordersMapper.OrdersToOrdersResponse(savedOrder);
    }

    @Override
    public OrdersResponse updateOrderStatus(Long id, OrderStatus status) {
        validationService.validateOrderId(id);
        Orders order = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setStatus(status);
        Orders savedOrder = ordersRepo.save(order);
        return ordersMapper.OrdersToOrdersResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        validationService.validateOrderId(id);
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("Order Not Found"));

        List<OrderDetails> orderDetails = orderDetailsRepo.findByOrderId(id);
        for(OrderDetails orderDetail : orderDetails) {
            orderDetailsRepo.delete(orderDetail);
        }
        ordersRepo.delete(order);
    }
}
