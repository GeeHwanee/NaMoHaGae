package kr.kro.namohagae.global.hander;

import kr.kro.namohagae.global.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketService webSocketService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       webSocketService.connect(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       webSocketService.disconnect(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
