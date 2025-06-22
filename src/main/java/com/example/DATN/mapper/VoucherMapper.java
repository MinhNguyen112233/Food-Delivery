package com.example.DATN.mapper;

import com.example.DATN.dto.req.VoucherRequest;
import com.example.DATN.dto.res.VoucherResponse;
import com.example.DATN.entities.Voucher;
import com.example.DATN.services.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {

    @Autowired
    private ValidationService validationService;

    public Voucher VoucherRequestToVoucher(VoucherRequest voucherRequest) {
        Voucher voucher = new Voucher();
        voucher.setDescription(voucherRequest.getDescription());
        voucher.setDiscountType(voucherRequest.getDiscountType());
        voucher.setDiscountValue(voucherRequest.getDiscountValue());
        voucher.setCode(voucherRequest.getCode());
        voucher.setStartDate(voucherRequest.getStartDate());
        voucher.setEndDate(voucherRequest.getEndDate());
        voucher.setMinOrderValue(voucherRequest.getMinOrderValue());
        voucher.setType(voucherRequest.getVoucherType());
        voucher.setMaxDiscountAmount(voucherRequest.getMaxDiscountAmount());

        // Set title theo DiscountType
        String title;
        if ("FIXED_AMOUNT".equalsIgnoreCase(voucherRequest.getDiscountType().toString())) {
            title = "Giảm " + voucherRequest.getDiscountValue() + " đ cho đơn hàng từ "
                    + voucherRequest.getMinOrderValue() + " đ";
        } else if ("PERCENTAGE".equalsIgnoreCase(voucherRequest.getDiscountType().toString())) {
            title = "Giảm " + voucherRequest.getDiscountValue() + "% tối đa "
                    + voucherRequest.getMaxDiscountAmount() + " đ cho đơn hàng từ "
                    + voucherRequest.getMinOrderValue() + " đ";
        } else {
            title = "Ưu đãi đặc biệt"; // fallback nếu không rõ loại
        }

        voucher.setTitle(title);
        return voucher;
    }


    public VoucherResponse VoucherToVoucherResponse(Voucher voucher) {
        VoucherResponse voucherResponse = new VoucherResponse();
        voucherResponse.setId(voucher.getId());
        voucherResponse.setTitle(voucher.getTitle());
        voucherResponse.setDescription(voucher.getDescription());
        voucherResponse.setDiscountType(voucher.getDiscountType());
        voucherResponse.setDiscountValue(voucher.getDiscountValue());
        voucherResponse.setCode(voucher.getCode());
        voucherResponse.setStartDate(voucher.getStartDate());
        voucherResponse.setEndDate(voucher.getEndDate());
        voucherResponse.setMinOrderValue(voucher.getMinOrderValue());
        voucherResponse.setVoucherType(voucher.getType());
        voucherResponse.setMaxDiscountAmount(voucher.getMaxDiscountAmount());
        return voucherResponse;
    }
}
