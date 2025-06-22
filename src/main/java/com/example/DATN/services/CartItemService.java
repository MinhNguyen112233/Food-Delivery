package com.example.DATN.services;

import com.example.DATN.dto.req.CartItemRequest;
import com.example.DATN.dto.res.CartItemResponse;

import java.util.List;

public interface CartItemService {
    CartItemResponse addCartItem(CartItemRequest request);

    CartItemResponse updateCartItem(Long id, CartItemRequest request);

    void deleteCartItem(Long id);

    List<CartItemResponse> getAllCartItems();

    CartItemResponse getCartItemById(Long id);

    List<CartItemResponse> getCartItemByCartId(Long id);
}
