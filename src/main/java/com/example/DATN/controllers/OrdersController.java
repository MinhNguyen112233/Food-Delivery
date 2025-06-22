package com.example.DATN.controllers;

import com.example.DATN.dto.req.OrdersRequest;
import com.example.DATN.dto.res.OrdersResponse;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class    OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-orders")
    public List<OrdersResponse> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('OWNER')")
    @GetMapping("/get-order-by-id")
    public OrdersResponse getOrderById(@RequestParam("id") Long id) {
        return ordersService.getOrderById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user")
    public List<OrdersResponse> getOrdersByUserId(@RequestParam("user_id") Long userId) {
        return ordersService.getOrdersByUserId(userId);
    }

    @GetMapping("/user_status")
    public List<OrdersResponse> getOrdersByUserIdAndStatusGroup(
            @RequestParam("user_id") Long userId,
            @RequestParam("group_id") int groupId
    ) {
        List<OrderStatus> statuses;

        switch (groupId) {
            case 1:
                statuses = List.of(OrderStatus.PENDING, OrderStatus.PREPARING, OrderStatus.DELIVERING , OrderStatus.CONFIRM_BY_SHIPPER , OrderStatus.CONFIRM_BY_SHOP, OrderStatus.WAITING_FOR_SHIPPER);
                break;
            case 2:
                statuses = List.of(OrderStatus.COMPLETED);
                break;
            case 3:
                statuses = List.of(OrderStatus.CANCELLED);
                break;
            case 4:
                statuses = List.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED);
                break;
            default:
                throw new IllegalArgumentException("Invalid group_id: " + groupId);
        }

        return ordersService.getOrdersByUserIdAndStatus(userId, statuses);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    @GetMapping("/food_shop_status")
    public List<OrdersResponse> getOrdersByFoodShopIdAndStatusGroup(
            @RequestParam("food_shop_id") Long foodShopId,
            @RequestParam("group_id") int groupId
    ) {
        List<OrderStatus> statuses;

        switch (groupId) {
            case 1: 
                statuses = List.of(OrderStatus.PENDING,OrderStatus.CONFIRM_BY_SHIPPER, OrderStatus.CONFIRM_BY_SHOP, OrderStatus.PREPARING , OrderStatus.WAITING_FOR_SHIPPER);
                break;
            case 2:
                statuses = List.of(OrderStatus.DELIVERING );
                break;
            case 3:
                statuses = List.of(OrderStatus.COMPLETED);
                break;
            case 4:
                statuses = List.of(OrderStatus.CANCELLED);
                break;
            default:
                throw new IllegalArgumentException("Invalid group_id: " + groupId);
        }

        return ordersService.getOrdersByFoodShopIdAndStatus(foodShopId, statuses);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-order")
    public OrdersResponse addOrder(@RequestParam("id") Long id , @RequestBody OrdersRequest request) {
        return ordersService.addOrder(id,request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-order")
    public OrdersResponse updateOrder(@RequestParam("id") Long id, @RequestBody OrdersRequest request) {
        return ordersService.updateOrder(id, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('OWNER')")
    @PutMapping("/update-order-status")
    public OrdersResponse updateOrderStatus(@RequestParam("id") Long id, @RequestParam("status") OrderStatus status) {
        return ordersService.updateOrderStatus(id, status);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete-order")
    public void deleteOrder(@RequestParam("id") Long id) {
        ordersService.deleteOrder(id);
    }
}
