package com.example.DATN.configs.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Kích hoạt WebSocket message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đăng ký endpoint cho kết nối WebSocket
        // SockJS được sử dụng để hỗ trợ các trình duyệt không hỗ trợ WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Định nghĩa các prefix cho các kênh message broker
        // '/app' là prefix cho các tin nhắn gửi đến ứng dụng
        registry.setApplicationDestinationPrefixes("/app");

        // '/topic' và '/queue' là prefix cho các tin nhắn gửi từ ứng dụng đến client
        // '/topic' thường dùng cho gửi message đến nhiều người
        // '/queue' thường dùng cho gửi message tới một người cụ thể
        registry.enableSimpleBroker("/topic", "/queue", "/user");

        // Định nghĩa prefix cho user destinations
        registry.setUserDestinationPrefix("/user");
    }
}


//package com.example.DATN.configs.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // WebSocket endpoint chính (không có SockJS)
//        registry.addEndpoint("/ws")
//                .setAllowedOriginPatterns("*") // Sử dụng patterns thay vì origins
//                .setAllowedOrigins("http://localhost:3000", "http://127.0.0.1:3000", "*"); // Thêm origins cụ thể nếu cần
//
//        // SockJS endpoint (cho web browsers không hỗ trợ WebSocket)
//        registry.addEndpoint("/ws-sockjs")
//                .setAllowedOriginPatterns("*")
//                .setAllowedOrigins("http://localhost:3000", "http://127.0.0.1:3000", "*")
//                .withSockJS()
//                .setHeartbeatTime(25000) // Thêm heartbeat để maintain connection
//                .setDisconnectDelay(5000);
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // Prefix cho các message gửi đến server
//        registry.setApplicationDestinationPrefixes("/app");
//
//        // Enable simple broker với heartbeat
//        registry.enableSimpleBroker("/topic", "/queue", "/user")
//                .setHeartbeatValue(new long[]{10000, 20000}); // [server, client] heartbeat intervals
//
//        // Prefix cho user-specific destinations
//        registry.setUserDestinationPrefix("/user");
//    }
//}