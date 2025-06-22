package com.example.DATN.dto.req;

import com.example.DATN.entities.ChatStatus;

// DTO để truyền tin nhắn qua WebSocket
public class ChatMessage {
    private MessageType type;
    private String content;
    private Long senderId;
    private Long orderId;
    private Long receiverId;
    private String senderName;
    private String time;
    private ChatStatus chatStatus;

    // Enum để định nghĩa các loại tin nhắn
    public enum MessageType {
        CHAT,       // Tin nhắn thông thường
        JOIN,       // Thông báo người dùng tham gia
        LEAVE       // Thông báo người dùng rời đi
    }

    // Getters and Setters
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ChatStatus getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(ChatStatus chatStatus) {
        this.chatStatus = chatStatus;
    }
}