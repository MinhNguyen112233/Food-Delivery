package com.example.DATN.controllers;

import com.example.DATN.dto.req.CartRequest;
import com.example.DATN.dto.res.CartResponse;
import com.example.DATN.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add-cart")
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest request) {
        CartResponse response = cartService.addCart(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-cart")
    public ResponseEntity<CartResponse> updateCart(@RequestParam("id") Long id, @RequestBody CartRequest request) {
        CartResponse response = cartService.updateCart(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete-cart")
    public ResponseEntity<Void> deleteCart(@RequestParam("id") Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-cart-by-id")
    public ResponseEntity<CartResponse> getCartById(@RequestParam("id") Long id) {
        CartResponse response = cartService.getCartById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-carts")
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        List<CartResponse> responseList = cartService.getAllCarts();
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-cart-user-foodshop")
    public ResponseEntity<CartResponse> getCartUserIdFoodShopId(@RequestParam("userId") Long userId, @RequestParam("foodShopId") Long foodShopId) {
        CartResponse response = cartService.getCartByUserIdAndFoodShopId(userId, foodShopId);
        return ResponseEntity.ok(response);
    }


}
