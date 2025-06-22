package com.example.DATN.controllers;

import com.example.DATN.dto.req.ChatMessage;
import com.example.DATN.entities.Chat;
import com.example.DATN.entities.ChatStatus;
import com.example.DATN.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Để gửi tin nhắn tới client

    @Autowired
    private ChatService chatService;



    // Xử lý khi có tin nhắn gửi đến endpoint '/app/chat.sendMessage'
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {

        System.out.println("Đã nhận tin nhắn từ: " + chatMessage.getSenderId() + " đến: " + chatMessage.getReceiverId());
        System.out.println("Nội dung: " + chatMessage.getContent());

        // Lưu tin nhắn vào database
        Chat savedChat = chatService.saveChat(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                chatMessage.getOrderId(),
                chatMessage.getContent()
        );

        System.out.println("Chat :" + savedChat);

        if (savedChat != null) {
            // Cập nhật thời gian cho tin nhắn
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            chatMessage.setTime(LocalDateTime.now().format(formatter));

            // Gửi tin nhắn tới người nhận cụ thể
            // '/queue/messages.{userId}' là destination cho người nhận
            messagingTemplate.convertAndSend(
                    "/queue/messages." + chatMessage.getReceiverId(),
                    chatMessage
            );

            // Gửi tin nhắn lại cho người gửi để xác nhận
            messagingTemplate.convertAndSend(
                    "/queue/messages." + chatMessage.getSenderId(),
                    chatMessage
            );
        }

        System.out.println("Đã gửi tin nhắn thành công");

    }

    // Xử lý khi có người tham gia cuộc trò chuyện
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {
        // Thông báo cho các user khác biết có người mới tham gia
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    // Xử lý khi người dùng đánh dấu tin nhắn đã đọc
    @MessageMapping("/chat.markRead")
    public void markRead(@Payload Long chatId) {
        chatService.markAsRead(chatId);
    }

    // Xử lý khi người dùng đánh dấu tất cả tin nhắn từ một người là đã đọc
    @MessageMapping("/chat.markAllRead")
    public void markAllRead(@Payload ChatMessage chatMessage) {
        chatService.markAllAsRead(chatMessage.getReceiverId(), chatMessage.getSenderId());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(
            @RequestParam Long userId1,
            @RequestParam Long userId2,
            @RequestParam Long orderId) {

        List<Chat> chatList = chatService.getChatHistory(userId1, userId2, orderId);
        return chatList.stream()
                .map(chatService::convertToDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-status")
    public ResponseEntity<String> updateChatStatusByOrderId(
            @RequestParam Long orderId,
            @RequestParam ChatStatus status) {

        try {
            chatService.updateChatStatusByOrderId(orderId, status);
            return ResponseEntity.ok("Cập nhật status chat thành công cho Order ID: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật status: " + e.getMessage());
        }
    }
}
