package com.example.DATN.controllers;

import com.example.DATN.dto.req.UserVoucherRequest;
import com.example.DATN.dto.res.UserVoucherResponse;
import com.example.DATN.entities.UserVoucher;
import com.example.DATN.services.UserVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user-vouchers")
public class UserVoucherController {

    private final UserVoucherService userVoucherService;

    @Autowired
    public UserVoucherController(UserVoucherService userVoucherService) {
        this.userVoucherService = userVoucherService;
    }

    /**
     * Get all user vouchers
     */
    @GetMapping
    public ResponseEntity<List<UserVoucherResponse>> getAllUserVouchers() {
        List<UserVoucherResponse> userVouchers = userVoucherService.getAllUserVouchers();
        return ResponseEntity.ok(userVouchers);
    }

    /**
     * Get a specific user voucher by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserVoucherResponse> getUserVoucherById(@PathVariable Long id) {
        UserVoucherResponse userVoucher = userVoucherService.getUserVoucherById(id);
        return ResponseEntity.ok(userVoucher);
    }

    /**
     * Get all vouchers for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserVoucherResponse>> getUserVouchersByUserId(@PathVariable Long userId) {
        List<UserVoucherResponse> userVouchers = userVoucherService.getUserVouchersByUserId(userId);
        return ResponseEntity.ok(userVouchers);
    }

    /**
     * Get only available vouchers for a specific user
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user/{userId}/status")
    public ResponseEntity<List<UserVoucherResponse>> getVouchersByUserIdAndStatus(@PathVariable Long userId, @RequestParam("status") UserVoucher.VoucherStatus status) {
        List<UserVoucherResponse> userVouchers = userVoucherService.getVouchersByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(userVouchers);
    }

    /**
     * Create a new user voucher
     */
    @PostMapping("/create-user-voucher")
    public ResponseEntity<UserVoucherResponse> createUserVoucher(@RequestBody UserVoucherRequest request) {
        UserVoucherResponse createdUserVoucher = userVoucherService.createUserVoucher(request);
        return new ResponseEntity<>(createdUserVoucher, HttpStatus.CREATED);
    }

    /**
     * Update an existing user voucher
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserVoucherResponse> updateUserVoucher(
            @PathVariable Long id,
            @RequestBody UserVoucherRequest request) {
        UserVoucherResponse updatedUserVoucher = userVoucherService.updateUserVoucher(id, request);
        return ResponseEntity.ok(updatedUserVoucher);
    }

    /**
     * Delete a user voucher
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserVoucher(@PathVariable Long id) {
        userVoucherService.deleteUserVoucher(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a voucher as used
     */
    @PostMapping("/{id}/use")
    public ResponseEntity<UserVoucherResponse> useVoucher(@PathVariable Long id) {
        UserVoucherResponse usedVoucher = userVoucherService.useVoucher(id);
        return ResponseEntity.ok(usedVoucher);
    }

    @GetMapping("/code-status")
    public ResponseEntity<UserVoucherResponse> getUserVoucherByUserIdAndCode(@RequestParam("id") Long id, @RequestParam("code") String code, @RequestParam("status") UserVoucher.VoucherStatus status) {
        UserVoucherResponse response = userVoucherService.getUserVoucherByUserIdAndCode(id, code, status);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/get-order-user-voucher")
    public ResponseEntity<List<UserVoucherResponse>> getOrderUserVoucher(@RequestParam("id") Long id, @RequestParam("minOrderValue") double minOrderValue, @RequestParam("endDate") LocalDateTime endDate) {
        List<UserVoucherResponse> userVouchers = userVoucherService.findAvailableVouchersWithStreamFilter(id, minOrderValue , endDate);
        return ResponseEntity.ok(userVouchers);
    }

}