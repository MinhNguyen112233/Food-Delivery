package com.example.DATN.mapper;

import com.example.DATN.dto.req.OrdersRequest;
import com.example.DATN.dto.res.OrdersResponse;
import com.example.DATN.entities.OrderStatus;
import com.example.DATN.entities.Orders;
import com.example.DATN.entities.PaymentMethod;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.repositories.AddressRepo;
import com.example.DATN.repositories.UserVoucherRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersMapper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private FoodShopMapper foodShopMapper;

    @Autowired
    private UserVoucherRepo userVoucherRepo;

    @Autowired
    private FoodShopRepo foodShopRepo;

    @Autowired
    private ValidationService validationService;

    public OrdersResponse OrdersToOrdersResponse(Orders orders) {
        OrdersResponse response = new OrdersResponse();
        validationService.validateOrderId(orders.getId());
        response.setId(orders.getId());
        response.setUserId(orders.getUser().getId());
        response.setAddressResponse(addressMapper.AddressToAddressResponse(orders.getAddress()));
        response.setTotalPrice(orders.getTotalPrice());
        response.setTotalOrderItem(orders.getTotalOrderItem());
        response.setTotalDelivery(orders.getTotalDelivery());
        response.setTotalApply(orders.getTotalApply());
        response.setTotalDiscount(orders.getTotalDiscount());
        response.setNote(orders.getNote());
        response.setDeliveryDoor(orders.isDeliveryDoor());
        response.setEatingUtensils(orders.isEatingUtensils());
        response.setStatus(orders.getStatus());
        response.setOrderCode(orders.getOrderCode());
        response.setPaymentMethod(orders.getPaymentMethod());
        if (orders.getUserVoucher() != null) {
            response.setUserVoucherId(orders.getUserVoucher().getId());
        } else {
            response.setUserVoucherId(null);
        }
        response.setFavourite(orders.isFavourite());
        response.setFoodShopResponse(foodShopMapper.FoodShopToFoodShopResponse(orders.getFoodShop()));
        response.setDistanceKm(orders.getDistanceKm());
        response.setEstimatedDeliveryTime(orders.getEstimatedDeliveryTime());
        response.setExpectedTime(orders.getExpectedTime());
        response.setCreatedAt(orders.getCreatedAt());
        return response;
    }

    public Orders OrdersRequestToOrders(OrdersRequest request) {
        Orders orders = new Orders();
        validationService.validateUserId(request.getUser_id());
        validationService.validateAddressId(request.getAddress_id());
        orders.setUser(userRepo.findById(request.getUser_id()).orElseThrow(() -> new RuntimeException("User Not Found")));
        orders.setAddress(addressRepo.findById(request.getAddress_id()).orElseThrow(() -> new RuntimeException("Address Not Found")));
        orders.setFoodShop(foodShopRepo.findById(request.getFood_shop_id()).orElseThrow(() -> new RuntimeException("Food Shop Not Found")));
        orders.setTotalPrice(request.getTotalPrice());
        orders.setTotalOrderItem(request.getTotalOrderItem());
        orders.setTotalDelivery(request.getTotalDelivery());
        orders.setTotalApply(request.getTotalApply());
        orders.setTotalDiscount(request.getTotalDiscount());
        orders.setNote(request.getNote());
        orders.setDeliveryDoor(request.isDeliveryDoor());
        orders.setEatingUtensils(request.isEatingUtensils());
        orders.setStatus(OrderStatus.valueOf(request.getStatus()));
        orders.setFavourite(request.isFavourite());
        if (request.getUser_voucher_id() != null) {
            orders.setUserVoucher(userVoucherRepo.findById(request.getUser_voucher_id())
                    .orElseThrow(() -> new RuntimeException("User Voucher Not Found")));
        } else {
            orders.setUserVoucher(null);
        }
        orders.setDistanceKm(request.getDistanceKm());
        orders.setEstimatedDeliveryTime(request.getEstimatedDeliveryTime());
        orders.setExpectedTime(request.getExpectedTime());
        orders.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        return orders;
    }
}
