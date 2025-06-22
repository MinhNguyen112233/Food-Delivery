
package com.example.DATN.services.impl;

import com.example.DATN.dto.req.AddressRequest;
import com.example.DATN.dto.res.AddressResponse;
import com.example.DATN.entities.Address;
import com.example.DATN.mapper.AddressMapper;
import com.example.DATN.repositories.AddressRepo;
import com.example.DATN.services.AddressService;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private ValidationService validationService;

    @Override
    public AddressResponse getAddressById(Long id) {
        validationService.validateAddressId(id);
        Optional<Address> addressOpt = addressRepo.findById(id);
        if (addressOpt.isPresent()) {
            return addressMapper.AddressToAddressResponse(addressOpt.get());
        }
        return null; // Trả về null nếu không tìm thấy
    }

//    @Override
//    public List<AddressResponse> getAllAddresses() {
//        List<Address> addresses = addressRepo.findAll();
//        if (addresses.isEmpty()) {
//            return Collections.emptyList(); // Trả về danh sách rỗng
//        }
//        return addresses.stream()
//                .map(addressMapper::AddressToAddressResponse)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<AddressResponse> getAllAddresses() {
        List<Address> addresses = addressRepo.findAll()
                .stream()
                .filter(address -> !Boolean.TRUE.equals(address.getDeleted()))
                .collect(Collectors.toList());

        if (addresses.isEmpty()) {
            return Collections.emptyList(); // Trả về danh sách rỗng
        }

        return addresses.stream()
                .map(addressMapper::AddressToAddressResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepo.findByUserIdAndDeletedFalse(userId);
        if (addresses.isEmpty()) {
            return Collections.emptyList(); // Trả về danh sách rỗng
        }
        return addresses.stream()
                .map(addressMapper::AddressToAddressResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse addAddress(AddressRequest request) {
        Address address = addressMapper.AddressRequestToAddress(request);
        System.out.println(address.getChoose());
        Address savedAddress = addressRepo.save(address);
        System.out.println(savedAddress.getChoose());
        return addressMapper.AddressToAddressResponse(savedAddress);
    }

    @Override
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        validationService.validateAddressId(id);
        Address address = addressRepo.findById(id).orElseThrow(() -> new RuntimeException("Address Not Found"));
        address = addressMapper.AddressRequestToAddress(request);
        address.setId(id);
        Address savedAddress = addressRepo.save(address);
        return addressMapper.AddressToAddressResponse(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        validationService.validateAddressId(id);
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address Not Found"));

        if (Boolean.TRUE.equals(address.getDeleted())) {
            throw new RuntimeException("Address already deleted");
        }

        Long userId = address.getUser().getId();
        boolean wasChosen = address.getChoose() == 1;

        // Soft delete
        address.setDeleted(true);
        address.setChoose(0);
        addressRepo.save(address);

        // Nếu địa chỉ vừa xóa là address được chọn, thì gán 1 address khác làm chosen
        if (wasChosen) {
            List<Address> remaining = addressRepo.findByUserIdAndDeletedFalse(userId);
            if (!remaining.isEmpty()) {
                Address newChosen = remaining.get(0);
                newChosen.setChoose(1);
                addressRepo.save(newChosen);
            }
        }
    }



    @Override
    public AddressResponse getAddressIsChoose(Long userId) {
        Address address = addressRepo.findChosenAddressByUserId(userId);
        if (address == null) {
            return null; // Trả về null nếu không tìm thấy
        }
        return addressMapper.AddressToAddressResponse(address);
    }

    @Override
    public List<AddressResponse> getAddressesByUserIdNotChoose(Long userId) {
        List<Address> addresses = addressRepo.findNotChosenAddressByUserId(userId);
        if (addresses.isEmpty()) {
            return Collections.emptyList(); // Trả về danh sách rỗng
        }
        return addresses.stream()
                .map(addressMapper::AddressToAddressResponse)
                .collect(Collectors.toList());
    }
}