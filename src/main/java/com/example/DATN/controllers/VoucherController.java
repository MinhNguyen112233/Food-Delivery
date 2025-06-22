package com.example.DATN.controllers;

import com.example.DATN.dto.req.VoucherRequest;
import com.example.DATN.dto.res.PublicVoucherResponse;
import com.example.DATN.dto.res.VoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import com.example.DATN.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getAllVouchers() {
        List<VoucherResponse> vouchers = voucherService.getAllVouchers();
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/active")
    public ResponseEntity<List<VoucherResponse>> getActiveVouchers() {
        List<VoucherResponse> activeVouchers = voucherService.getActiveVouchers();
        return ResponseEntity.ok(activeVouchers);
    }

    @GetMapping("/get-voucher/{id}")
    public ResponseEntity<VoucherResponse> getVoucherById(@PathVariable Long id) {
        VoucherResponse voucher = voucherService.getVoucherById(id);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<VoucherResponse> getVoucherByCode(@PathVariable String code) {
        VoucherResponse voucher = voucherService.getVoucherByCode(code);
        return ResponseEntity.ok(voucher);
    }

    @PostMapping("/create-voucher")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        VoucherResponse createdVoucher = voucherService.createVoucher(voucherRequest);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherResponse> updateVoucher(
            @PathVariable Long id,
            @RequestBody VoucherRequest voucherRequest) {
        VoucherResponse updatedVoucher = voucherService.updateVoucher(id, voucherRequest);
        return ResponseEntity.ok(updatedVoucher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-active-voucher")
    public List<VoucherResponse> getActiveVoucher(@RequestParam("userId") Long userId, @RequestParam("minOrderValue") BigDecimal minOrderValue , @RequestParam("endDate")LocalDateTime endDate) {
        return voucherService.filterValidVouchers(userId,minOrderValue, endDate);
    }

    @GetMapping("/get-voucher-status")
    public List<VoucherResponse> getVoucherStatus(@RequestParam("userId") Long userId, @RequestParam("status") UserVoucher.VoucherStatus status) {
        return voucherService.getVoucherStatus(userId , status);
    }

    @GetMapping("/get-voucher-welcome")
    public List<VoucherResponse> getVoucherWelcome() {
        return voucherService.getVoucherWelcome();
    }

    @GetMapping("/get-voucher-public")
    public List<PublicVoucherResponse> getVoucherPublic(@RequestParam("id") Long id) {
        return voucherService.getPublicVouchersWithUser(id);
    }
}