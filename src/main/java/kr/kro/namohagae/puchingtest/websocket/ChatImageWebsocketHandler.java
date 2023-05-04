package kr.kro.namohagae.puchingtest.websocket;

import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatImageWebsocketHandler extends BinaryWebSocketHandler {

    @Autowired
    private ChatService chatService;

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username= session.getPrincipal().getName();
        session.getAttributes().put("username", username);
        sessions.put(username,session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("이미지웹소켓에러@@@@@@");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      String username= session.getPrincipal().getName();
        sessions.remove(username);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
