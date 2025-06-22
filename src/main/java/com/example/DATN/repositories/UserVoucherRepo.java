package com.example.DATN.repositories;

import com.example.DATN.entities.UserVoucher;
import com.example.DATN.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVoucherRepo extends JpaRepository<UserVoucher, Long> {
    List<UserVoucher> findByUserId(Long userId);

    List<UserVoucher> findByUserIdAndStatus(Long userId, UserVoucher.VoucherStatus status);

    Long countByUserIdAndVoucherId(Long userId, Long voucherId);

    @Query("SELECT uv.voucher FROM UserVoucher uv WHERE uv.userId = :userId AND uv.status = :status")
    List<Voucher> findVouchersByUserIdAndStatus(@Param("userId") Long userId, @Param("status") UserVoucher.VoucherStatus status);

    @Query("SELECT uv.voucher.id FROM UserVoucher uv WHERE uv.userId = :userId")
    List<Long> findVoucherIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT uv FROM UserVoucher uv " +
            "JOIN uv.voucher v " +
            "WHERE v.code = :code " +
            "AND uv.userId = :userId " +
            "AND uv.status = :status")
    UserVoucher findByVoucherCodeAndUserIdAndStatus(
            @Param("code") String code,
            @Param("userId") Long userId,
            @Param("status") UserVoucher.VoucherStatus status
    );
}
