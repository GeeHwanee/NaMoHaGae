package kr.kro.namohagae.global.config;

import kr.kro.namohagae.global.hander.NotificationWebSocketHandler;
import kr.kro.namohagae.puching.websocket.ChatPuchingWebsocketHandler;
import kr.kro.namohagae.puching.websocket.ChatWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;
    @Autowired
    private ChatPuchingWebsocketHandler chatImageWebsocketHandler;
    @Autowired
    private NotificationWebSocketHandler notificationWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 소켓연결 주소와 소켓접근가능 도메안 설정
        registry.addHandler(chatWebSocketHandler,"/chatroom").setAllowedOrigins("http://localhost:8081");
        registry.addHandler(chatImageWebsocketHandler,"/puching").setAllowedOrigins(("http://localhost:8081:"));
        registry.addHandler(notificationWebSocketHandler, "/notification").setAllowedOrigins("http://localhost:8081");
    }
}
