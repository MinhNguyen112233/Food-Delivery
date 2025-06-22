package com.example.DATN.mapper;

import com.example.DATN.dto.req.AddressRequest;
import com.example.DATN.dto.res.AddressResponse;
import com.example.DATN.entities.Address;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValidationService validationService;

    public AddressResponse AddressToAddressResponse(Address address) {
        AddressResponse response = new AddressResponse();
        validationService.validateAddressId(address.getId());
        response.setId(address.getId());
        response.setUser(userMapper.UserToUserResponse(address.getUser())); // Assuming you have a method to convert User to UserResponse
        response.setFullName(address.getFullName());
        response.setPhone(address.getPhone());
        response.setAddress(address.getAddress());
        response.setLatitude(address.getLatitude());
        response.setLongitude(address.getLongitude());
        System.out.println("Choose Response: " + address.getChoose());
        response.setChoose(address.getChoose());
        return response;
    }

    public Address AddressRequestToAddress(AddressRequest request) {
        Address address = new Address();
        validationService.validateUserId(request.getUser_id());
        address.setUser(userRepo.findById(request.getUser_id()).orElseThrow(() -> new RuntimeException("User Not Found")));
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setAddress(request.getAddress());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        System.out.println("Choose Request: " + address.getChoose());
        address.setChoose(request.getChoose());
        return address;
    }
}
