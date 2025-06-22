package com.example.DATN.services.impl;

import com.example.DATN.dto.req.CartRequest;
import com.example.DATN.dto.res.CartResponse;
import com.example.DATN.entities.Cart;
import com.example.DATN.mapper.CartMapper;
import com.example.DATN.repositories.CartRepo;
import com.example.DATN.services.CartService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ValidationService validationService;

    @Override
    public CartResponse getCartById(Long id) {
        validationService.validateCartId(id);
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        return cartMapper.CartToCartResponse(cart);
    }

    @Override
    public CartResponse getCartByUserIdAndFoodShopId(Long userId, Long foodShopId) {
        validationService.validateUserId(userId);
        validationService.validateFoodShopId(foodShopId);
        Cart cart = cartRepo.findByUserIdAndFoodShopId(userId, foodShopId);
        System.out.println("get dslkfjskdf");
        return cartMapper.CartToCartResponse(cart);
    }

    @Override
    public List<CartResponse> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();
        return carts.stream()
                .map(cartMapper::CartToCartResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse addCart(CartRequest request) {
        Cart cart = cartMapper.CartRequestToCart(request);
        
        cartRepo.save(cart);
        return cartMapper.CartToCartResponse(cart);
    }

    @Override
    public CartResponse updateCart(Long id, CartRequest request) {
        validationService.validateCartId(id);
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        cart = cartMapper.CartRequestToCart(request);
        cart.setId(id);
        cartRepo.save(cart);
        return cartMapper.CartToCartResponse(cart);
    }

    @Override
    public void deleteCart(Long id) {
        validationService.validateCartId(id);
        cartRepo.deleteById(id);
    }
}
