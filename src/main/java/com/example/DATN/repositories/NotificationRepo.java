package com.example.DATN.repositories;

import com.example.DATN.entities.NotificationFcm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationFcm, Long> {

    @Query(value = "SELECT * FROM notifications n WHERE n.created_at BETWEEN :startDate AND :endDate" , nativeQuery = true)
    List<NotificationFcm> findNotificationsFromLastWeek(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
