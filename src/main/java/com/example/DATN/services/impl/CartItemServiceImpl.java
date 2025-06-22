package com.example.DATN.services.impl;

import com.example.DATN.dto.req.CartItemRequest;
import com.example.DATN.dto.res.CartItemResponse;
import com.example.DATN.entities.CartItem;
import com.example.DATN.mapper.CartItemMapper;
import com.example.DATN.repositories.CartItemRepo;
import com.example.DATN.repositories.CartRepo;
import com.example.DATN.repositories.FoodRepo;
import com.example.DATN.services.CartItemService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private CartItemRepo cartItemRepository;

    @Autowired
    private ValidationService validationService;

//    @Override
//    public CartItemResponse addCartItem(CartItemRequest request) {
//        CartItem cartItem = cartItemMapper.cartItemRequestToCartItem(request);
//        cartItemRepository.save(cartItem);
//        return cartItemMapper.cartItemToCartItemResponse(cartItem);
//    }

    @Override
    public CartItemResponse addCartItem(CartItemRequest request) {
        // Tìm CartItem dựa trên cart_id và food_id
        Optional<CartItem> existingCartItemOptional = cartItemRepository.findByCartIdAndFoodId(request.getCart_id(), request.getFood_id());

        CartItem cartItem;
        if (existingCartItemOptional.isPresent()) {
            // Nếu CartItem đã tồn tại, cộng số lượng
            cartItem = existingCartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setNote(request.getNote()); // Cập nhật ghi chú nếu cần
        } else {
            // Nếu chưa tồn tại, tạo mới
            cartItem = cartItemMapper.cartItemRequestToCartItem(request);
        }

        // Lưu CartItem vào cơ sở dữ liệu
        cartItemRepository.save(cartItem);

        // Chuyển đổi thành CartItemResponse để trả về
        return cartItemMapper.cartItemToCartItemResponse(cartItem);
    }


    @Override
    public CartItemResponse updateCartItem(Long id, CartItemRequest request) {
        validationService.validateCartItemId(id);
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("CartItem Not Found"));
        cartItem = cartItemMapper.cartItemRequestToCartItem(request);
        cartItem.setId(id);
        cartItemRepository.save(cartItem);
        return cartItemMapper.cartItemToCartItemResponse(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItemResponse> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItemMapper::cartItemToCartItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponse getCartItemById(Long id) {
        validationService.validateCartItemId(id);
        return cartItemRepository.findById(id)
                .map(cartItemMapper::cartItemToCartItemResponse)
                .orElseThrow(() -> new RuntimeException("CartItem Not Found"));
    }

    @Override
    public List<CartItemResponse> getCartItemByCartId(Long id) {
        validationService.validateCartId(id);
        return cartItemRepository.findCartItemByCartId(id).stream()
                .map(cartItemMapper :: cartItemToCartItemResponse)
                .collect(Collectors.toList());
    }
}
