package kr.kro.namohagae.puchingtest.websocket;

import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    private ChatService service;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = getUsername(session);
        sessions.put(username, session);
        // WebSocket 연결이 성공적으로 열리면 호출됩니다.
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = getUsername(session);
        String receiverUsername = (String) session.getAttributes().get("receiverUsername");
        String payload = message.getPayload().toString();

        if (receiverUsername != null) {
            // 수신자가 지정된 경우, 수신자에게만 메시지를 전송합니다.
            WebSocketSession receiverSession = sessions.get(receiverUsername);
            if (receiverSession != null && receiverSession.isOpen()) {
                TextMessage textMessage = new TextMessage(payload);
                receiverSession.sendMessage(textMessage);
            }
        } else {
            // 수신자가 지정되지 않은 경우, 모든 클라이언트에게 메시지를 전송합니다.
            for (WebSocketSession clientSession : sessions.values()) {
                if (clientSession.isOpen()) {
                    TextMessage textMessage = new TextMessage(payload);
                    clientSession.sendMessage(textMessage);
                }
            }
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // WebSocket 통신 중 에러가 발생하면 호출됩니다.
        System.out.println("웹소켓이 에러났당@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String username = getUsername(session);
        sessions.remove(username);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getUsername(WebSocketSession session) {
        return (String) session.getAttributes().get("username");
    }
}
