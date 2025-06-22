package com.example.DATN.controllers;

import com.example.DATN.dto.req.AddressRequest;
import com.example.DATN.dto.res.AddressResponse;
import com.example.DATN.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-address")
    public List<AddressResponse> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getAddressById")
    public AddressResponse getAddressById(@RequestParam("id") Long id) {
        return addressService.getAddressById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getAddressIsChoose")
    public AddressResponse getAddressIsChoose(@RequestParam("id") Long userId) {
        return addressService.getAddressIsChoose(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getAddressByUserId")
    public List<AddressResponse> getAddressesByUserId(@RequestParam("user_id") Long userId) {
        return addressService.getAddressesByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @PostMapping("/add-address")
    public AddressResponse addAddress(@RequestBody AddressRequest request) {
        return addressService.addAddress(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-address")
    public AddressResponse updateAddress(@RequestParam("id") Long id, @RequestBody AddressRequest request) {
        return addressService.updateAddress(id, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/delete-address")
    public void deleteAddress(@RequestParam("id") Long id) {
        addressService.deleteAddress(id);
    }

    @GetMapping("/getAddressNotChoose")
    public List<AddressResponse> getAddressesNotChoose(@RequestParam("user_id") Long userId){
        return addressService.getAddressesByUserIdNotChoose(userId);
    }
}
