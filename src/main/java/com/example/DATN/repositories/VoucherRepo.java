package com.example.DATN.repositories;

import com.example.DATN.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, Long> {
    Voucher findByCode(String code);
    List<Voucher> findByEndDateAfter(LocalDateTime date);
    List<Voucher> findByType(Voucher.VoucherType type);
    // làm thêm hàm lấy list voucher có value > minOrderValue và ngày hiện tại < endDate
    List<Voucher> findByMinOrderValueLessThanEqualAndEndDateAfter(BigDecimal minOrderValue, LocalDateTime currentDate);
}
