package com.example.DATN.controllers;

import com.example.DATN.configs.vnpay.VNPayConfig;
import com.example.DATN.dto.req.PaymentRequest;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/vnpays")
public class VNPayController {

    @Autowired
    private VNPayConfig config;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                           @RequestParam("amount") int amount,
                                           @RequestParam("orderInfo") String orderInfo ) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = config.getRandomNumber(8);
        String vnp_IpAddr = config.getIpAddress(request);
        String vnp_TmnCode = config.vnp_TmnCode;
        String orderType = "order-type";


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
        vnp_Params.put("vnp_CurrCode", "VND");

        String orderInfor = orderInfo;

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        String urlReturn =  config.vnp_Returnurl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        //vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_IpAddr", "0.0.0.0");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String salt = config.vnp_HashSecret;
        System.out.println("Hash Data : " + hashData.toString());
        String vnp_SecureHash = config.hmacSHA512(salt, hashData.toString());
        System.out.println("Hash Data Secure : " + vnp_SecureHash);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = config.vnp_PayUrl + "?" + queryUrl;
        System.out.println("Payment Url : " + paymentUrl);
        PaymentRequest payment = new PaymentRequest();
        payment.setStatus("OK");
        payment.setMessage(vnp_IpAddr);
        payment.setUrlPayment(paymentUrl);

        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

//    @GetMapping("/vnpay-payment-return")
//    public ResponseEntity<?> vnpayReturn(@RequestParam Map<String, String> vnpParams) {
//        // Xử lý dữ liệu từ VNPay (ví dụ: xác minh chữ ký)
//        boolean isValid = VNPayConfig.verifySignature(vnpParams);
//
//        if (isValid) {
//            // Lưu thông tin giao dịch vào database, cập nhật trạng thái đơn hàng
//            String orderId = vnpParams.get("vnp_TxnRef");
//            String status = vnpParams.get("vnp_ResponseCode"); // 00 = thành công
//
//            // Chuyển hướng sang URL khác sau khi xử lý xong
//            String customRedirectUrl = "https://your-frontend.com/payment-success?orderId=" + orderId + "&status=" + status;
//            return ResponseEntity.status(HttpStatus.FOUND).header("Location", customRedirectUrl).build();
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
//        }
//    }
}
