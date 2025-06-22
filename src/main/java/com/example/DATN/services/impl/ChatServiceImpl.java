package com.example.DATN.services.impl;

import com.example.DATN.dto.req.ChatMessage;
import com.example.DATN.entities.*;
import com.example.DATN.repositories.ChatRepository;
import com.example.DATN.repositories.FoodShopRepo;
import com.example.DATN.repositories.OrderRepo;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.ChatService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Chat saveChat(Long senderId, Long receiverId, Long orderId, String content) {
        Optional<User> senderOptional = userRepository.findById(senderId);
        Optional<User> receiverOptional = userRepository.findById(receiverId);
        Optional<Orders> orderOptional = orderRepo.findById(orderId);

        if (senderOptional.isPresent() && receiverOptional.isPresent() && orderOptional.isPresent()) {
            User sender = senderOptional.get();
            User receiver = receiverOptional.get();
            Orders order = orderOptional.get();

            Chat chat = new Chat();
            chat.setContent(content);
            chat.setSender(sender);
            chat.setReceiver(receiver);
            chat.setOrder(order);  // liên kết Order
            chat.setSentAt(LocalDateTime.now());
            chat.setRead(false);
            chat.setChatStatus(ChatStatus.ACTIVE); // mặc định

            return chatRepository.save(chat);
        }

        return null;
    }


    @Override
    public List<Chat> getChatHistory(Long userId1, Long userId2, Long orderId) {
        return chatRepository.findChatsBetweenUsers(userId1, userId2, orderId);
    }

    @Override
    public void markAsRead(Long chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            chat.setRead(true);
            chatRepository.save(chat);
        }
    }

    // Đánh dấu tất cả tin nhắn từ một người dùng là đã đọc
    @Override
    public void markAllAsRead(Long receiverId, Long senderId) {
        Optional<User> receiverOptional = userRepository.findById(receiverId);
        Optional<User> senderOptional = userRepository.findById(senderId);

        if (receiverOptional.isPresent() && senderOptional.isPresent()) {
            User receiver = receiverOptional.get();
            User sender = senderOptional.get();

            List<Chat> unreadChats = chatRepository.findByReceiverAndIsReadFalse(receiver);
            for (Chat chat : unreadChats) {
                if (chat.getSender().getId().equals(senderId)) {
                    chat.setRead(true);
                    chatRepository.save(chat);
                }
            }
        }
    }

    @Override
    public void updateChatStatusByOrderId(Long orderId, ChatStatus newStatus) {
        Optional<Orders> orderOptional = orderRepo.findById(orderId);

        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();

            // Tìm tất cả chat theo orderId
            List<Chat> chatList = chatRepository.findByOrder(order);

            // Cập nhật status cho tất cả chat
            for (Chat chat : chatList) {
                chat.setChatStatus(newStatus);
            }

            // Lưu tất cả thay đổi
            chatRepository.saveAll(chatList);

            System.out.println("Đã cập nhật status cho " + chatList.size() + " tin nhắn của Order ID: " + orderId + " thành: " + newStatus);
        } else {
            System.out.println("Không tìm thấy Order với ID: " + orderId);
        }
    }

    // Chuyển đổi Chat entity thành ChatMessage DTO
    @Override
    public ChatMessage convertToDTO(Chat chat) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setContent(chat.getContent());
        chatMessage.setSenderId(chat.getSender().getId());
        chatMessage.setReceiverId(chat.getReceiver().getId());
        chatMessage.setOrderId(chat.getOrder().getId());
        chatMessage.setSenderName(chat.getSender().getFullName());

        // Format thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatMessage.setTime(chat.getSentAt().format(formatter));

        return chatMessage;
    }
}
