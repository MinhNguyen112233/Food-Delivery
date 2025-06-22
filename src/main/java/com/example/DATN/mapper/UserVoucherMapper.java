package com.example.DATN.mapper;

import com.example.DATN.dto.req.UserVoucherRequest;
import com.example.DATN.dto.res.UserVoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.repositories.VoucherRepo;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserVoucherMapper {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private VoucherRepo voucherRepo;

    @Autowired
    private VoucherMapper voucherMapper;

    public UserVoucher UserVoucherRequestToUserVoucher(UserVoucherRequest request) {
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setUserId(request.getUserId());
        userVoucher.setVoucher(voucherRepo.findById(request.getVoucherId()).get());
        userVoucher.setStatus(request.getStatus());
        userVoucher.setUsedAt(request.getUsedAt());
        return userVoucher;
    }

    public UserVoucherResponse UserVoucherToUserVoucherResponse(UserVoucher userVoucher) {
        UserVoucherResponse userVoucherResponse = new UserVoucherResponse();
        userVoucherResponse.setId(userVoucher.getId());
        userVoucherResponse.setUserId(userVoucher.getUserId());
        userVoucherResponse.setVoucher(voucherMapper.VoucherToVoucherResponse(userVoucher.getVoucher()));
        userVoucherResponse.setStatus(userVoucher.getStatus());
        userVoucherResponse.setUsedAt(userVoucher.getUsedAt());
        return userVoucherResponse;
    }

}
