package com.example.DATN.mapper;

import com.example.DATN.dto.req.CartItemRequest;
import com.example.DATN.dto.res.CartItemResponse;
import com.example.DATN.entities.CartItem;
import com.example.DATN.mapper.CartMapper;
import com.example.DATN.mapper.FoodMapper;
import com.example.DATN.repositories.CartRepo;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private ValidationService validationService;

    public CartItemResponse cartItemToCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        validationService.validateCartItemId(cartItem.getId());
        cartItemResponse.setId(cartItem.getId());
        validationService.validateCartId(cartItem.getCart().getId());
        validationService.validateFoodId(cartItem.getFood().getId());
        cartItemResponse.setCart(cartMapper.CartToCartResponse(cartItem.getCart()));
        cartItemResponse.setFood(foodMapper.FoodToFoodResponse(cartItem.getFood()));
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setNote(cartItem.getNote());
        return cartItemResponse;
    }

    public CartItem cartItemRequestToCartItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem();
        validationService.validateCartId(cartItemRequest.getCart_id());
        validationService.validateFoodId(cartItemRequest.getFood_id());
        cartItem.setCart(cartRepo.findById(cartItemRequest.getCart_id()).orElse(null));
        cartItem.setFood(foodRepo.findById(cartItemRequest.getFood_id()).orElse(null));
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setNote(cartItemRequest.getNote());
        return cartItem;
    }
}
