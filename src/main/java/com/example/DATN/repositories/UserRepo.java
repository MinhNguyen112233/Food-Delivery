//package com.example.DATN.repositories;
//
//import com.example.DATN.entities.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface UserRepo extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
//    Optional<User> findById(Long id);
//    Optional<User> findByPhoneNumber(String phoneNumber);
//}


package com.example.DATN.repositories;

import com.example.DATN.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // Tìm theo email với tùy chọn includeDeleted
    @Query("SELECT u FROM User u WHERE u.email = :email AND (:includeDeleted = true OR u.deleted = false)")
    Optional<User> findByEmail(@Param("email") String email, @Param("includeDeleted") Boolean includeDeleted);

    // Overload method giữ nguyên signature cũ (mặc định là false)
    default Optional<User> findByEmail(String email) {
        return findByEmail(email, false);
    }

    // Tìm theo ID với tùy chọn includeDeleted
    @Query("SELECT u FROM User u WHERE u.id = :id AND (:includeDeleted = true OR u.deleted = false)")
    Optional<User> findById(@Param("id") Long id, @Param("includeDeleted") Boolean includeDeleted);

    // Override method từ JpaRepository để mặc định không lấy deleted
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = false")
    Optional<User> findById(@Param("id") Long id);

    // Tìm theo phoneNumber với tùy chọn includeDeleted
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND (:includeDeleted = true OR u.deleted = false)")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("includeDeleted") Boolean includeDeleted);

    // Overload method giữ nguyên signature cũ
    default Optional<User> findByPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber, false);
    }

    // Lấy tất cả users với tùy chọn includeDeleted
    @Query("SELECT u FROM User u WHERE (:includeDeleted = true OR u.deleted = false)")
    Page<User> findAll(Pageable pageable, @Param("includeDeleted") Boolean includeDeleted);

    // Override method từ JpaRepository để mặc định không lấy deleted
    @Query("SELECT u FROM User u WHERE u.deleted = false")
    Page<User> findAll(Pageable pageable);

    // Lấy chỉ những user đã xóa mềm
    @Query("SELECT u FROM User u WHERE u.deleted = true")
    Page<User> findAllDeleted(Pageable pageable);

    // Tìm user đã xóa mềm theo ID
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = true")
    Optional<User> findDeletedById(@Param("id") Long id);

    // Tìm user đã xóa mềm theo email
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deleted = true")
    Optional<User> findDeletedByEmail(@Param("email") String email);

    // Tìm user đã xóa mềm theo phone
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND u.deleted = true")
    Optional<User> findDeletedByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}