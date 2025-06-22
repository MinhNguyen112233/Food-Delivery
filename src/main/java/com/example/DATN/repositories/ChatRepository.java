package com.example.DATN.repositories;

import com.example.DATN.entities.Chat;
import com.example.DATN.entities.Orders;
import com.example.DATN.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    // Lấy tất cả tin nhắn giữa 2 người dùng


        @Query("SELECT c FROM Chat c WHERE " +
                "((c.sender.id = :userId1 AND c.receiver.id = :userId2) OR (c.sender.id = :userId2 AND c.receiver.id = :userId1)) " +
                "AND c.order.id = :orderId " +
                "AND c.chatStatus = 'ACTIVE' " +
                "ORDER BY c.sentAt ASC")
        List<Chat> findChatsBetweenUsers(Long userId1, Long userId2, Long orderId);

    List<Chat> findByOrder(Orders order);

    // Hoặc nếu bạn muốn query trực tiếp bằng orderId
    @Query("SELECT c FROM Chat c WHERE c.order.id = :orderId")
    List<Chat> findByOrderId(@Param("orderId") Long orderId);

    // Lấy danh sách tin nhắn chưa đọc của một người dùng
    List<Chat> findByReceiverAndIsReadFalse(User receiver);

}