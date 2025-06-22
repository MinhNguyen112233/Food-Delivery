package com.example.DATN.controllers;

import com.example.DATN.dto.req.CartItemRequest;
import com.example.DATN.dto.res.CartItemResponse;
import com.example.DATN.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-cart-item")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody CartItemRequest request) {
        CartItemResponse response = cartItemService.addCartItem(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-cart-item")
    public ResponseEntity<CartItemResponse> updateCartItem(@RequestParam("id") Long id, @RequestBody CartItemRequest request) {
        CartItemResponse response = cartItemService.updateCartItem(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete-cart-item")
    public ResponseEntity<Void> deleteCartItem(@RequestParam("id") Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-cart-items")
    public ResponseEntity<List<CartItemResponse>> getAllCartItems() {
        List<CartItemResponse> responses = cartItemService.getAllCartItems();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-cart-item-by-id")
    public ResponseEntity<CartItemResponse> getCartItemById(@RequestParam("id") Long id) {
        CartItemResponse response = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/cart-item-cart-id")
    public ResponseEntity<List<CartItemResponse>> getCartItemByCartId(@RequestParam("id") Long id) {
        List<CartItemResponse> response = cartItemService.getCartItemByCartId(id);
        return ResponseEntity.ok(response);
    }

}
