package com.example.DATN.repositories;

import com.example.DATN.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByUserIdAndDeletedFalse(Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isChoose = 1 AND a.deleted = false")
    Address findChosenAddressByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isChoose = 0 AND a.deleted = false")
    List<Address> findNotChosenAddressByUserId(@Param("userId") Long userId);

//    List<Address> findByUserId(Long Id);
//
//    @Query(value = "SELECT * FROM address WHERE user_id = :userId AND is_choose = 1", nativeQuery = true)
//    Address findChosenAddressByUserId(@Param("userId") Long userId);
//
//    @Query(value = "SELECT * FROM address WHERE user_id = :userId AND is_choose = 0", nativeQuery = true)
//    List<Address> findNotChosenAddressByUserId(@Param("userId") Long userId);
}
