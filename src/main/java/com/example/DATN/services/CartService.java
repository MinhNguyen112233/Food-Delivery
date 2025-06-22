package com.example.DATN.services;

import com.example.DATN.dto.req.CartRequest;
import com.example.DATN.dto.res.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse getCartById(Long id);

    CartResponse getCartByUserIdAndFoodShopId(Long userId, Long foodShopId);

    List<CartResponse> getAllCarts();

    CartResponse addCart(CartRequest request);

    CartResponse updateCart(Long id, CartRequest request);

    void deleteCart(Long id);

}
