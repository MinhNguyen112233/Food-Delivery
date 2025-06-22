package com.example.DATN.services;

import com.example.DATN.dto.req.AddressRequest;
import com.example.DATN.dto.res.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse getAddressById(Long id);
    List<AddressResponse> getAllAddresses();
    List<AddressResponse> getAddressesByUserId(Long userId);
    AddressResponse addAddress(AddressRequest request);
    AddressResponse updateAddress(Long id, AddressRequest request);
    void deleteAddress(Long id);
    AddressResponse getAddressIsChoose(Long userId);
    List<AddressResponse> getAddressesByUserIdNotChoose(Long userId);
}
