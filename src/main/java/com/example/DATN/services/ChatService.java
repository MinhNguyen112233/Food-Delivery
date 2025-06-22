package com.example.DATN.services;

import com.example.DATN.dto.req.ChatMessage;
import com.example.DATN.entities.Chat;
import com.example.DATN.entities.ChatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatService {
    // Lưu tin nhắn vào database
    Chat saveChat(Long senderId, Long receiverId,Long orderId, String content);

    // Lấy lịch sử chat giữa 2 người dùng
    List<Chat> getChatHistory(Long userId1, Long userId2 , Long foodShopId);

    // Đánh dấu tin nhắn là đã đọc
    void markAsRead(Long chatId);

    // Đánh dấu tất cả tin nhắn từ một người dùng là đã đọc
    void markAllAsRead(Long receiverId, Long senderId);

    void updateChatStatusByOrderId(Long orderId, ChatStatus newStatus);

    // Chuyển đổi Chat entity thành ChatMessage DTO
    ChatMessage convertToDTO(Chat chat);
}
