package com.example.DATN.controllers;

import com.example.DATN.dto.req.OrderDetailsRequest;
import com.example.DATN.dto.res.OrderDetailsResponse;
import com.example.DATN.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/create-order-details")
    public ResponseEntity<OrderDetailsResponse> createOrderDetails(@RequestBody OrderDetailsRequest request) {
        OrderDetailsResponse response = orderDetailsService.addOrderDetails(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-order-details")
    public ResponseEntity<OrderDetailsResponse> updateOrderDetails(@RequestParam("id") Long id, @RequestBody OrderDetailsRequest request) {
        OrderDetailsResponse response = orderDetailsService.updateOrderDetails(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-order-details")
    public ResponseEntity<Void> deleteOrderDetails(@RequestParam("id") Long id) {
        orderDetailsService.deleteOrderDetails(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('OWNER')")
    @GetMapping("/order")
    public ResponseEntity<List<OrderDetailsResponse>> getOrderDetailsByOrderId(@RequestParam("id") Long orderId) {
        List<OrderDetailsResponse> responseList = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/id")
    public ResponseEntity<OrderDetailsResponse> getOrderDetailsById(@RequestParam("id") Long id) {
        OrderDetailsResponse response = orderDetailsService.getOrderDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-order-details")
    public ResponseEntity<List<OrderDetailsResponse>> getAllOrderDetails() {
        List<OrderDetailsResponse> responseList = orderDetailsService.getAllOrderDetails();
        return ResponseEntity.ok(responseList);
    }
}
