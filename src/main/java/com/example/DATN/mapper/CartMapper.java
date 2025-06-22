package com.example.DATN.mapper;

import com.example.DATN.dto.req.CartRequest;
import com.example.DATN.dto.res.CartResponse;
import com.example.DATN.entities.Cart;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartMapper {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public FoodShopMapper foodShopMapper;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public FoodShopRepo foodShopRepo;

    @Autowired
    public ValidationService validationService;

    public CartResponse CartToCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        validationService.validateCartId(cart.getId());
        cartResponse.setId(cart.getId());
        cartResponse.setUser(userMapper.UserToUserResponse(cart.getUser()));
        cartResponse.setFoodShop(foodShopMapper.FoodShopToFoodShopResponse(cart.getFoodShop()));

        return cartResponse;
    }

    public Cart CartRequestToCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        validationService.validateUserId(cartRequest.getUser_id());
        validationService.validateFoodShopId(cartRequest.getFoodShop_id());
        cart.setUser(userRepo.findById(cartRequest.getUser_id()).orElse(null));
        cart.setFoodShop(foodShopRepo.findById(cartRequest.getFoodShop_id()).orElse(null));

        return cart;
    }
}
