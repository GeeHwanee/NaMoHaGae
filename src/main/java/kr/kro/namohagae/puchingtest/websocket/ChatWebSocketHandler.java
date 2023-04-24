package kr.kro.namohagae.puchingtest.websocket;

import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    private ChatService service;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();
        if (principal != null) {
            // Principal 객체에서 사용자의 ID를 추출합니다.
            String userId = principal.getName();
            session.getAttributes().put("userId", userId);
        }
        // WebSocket 연결이 성공적으로 열리면 호출됩니다.
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        // 클라이언트로부터 메시지가 수신되면 호출됩니다.
        String payload = message.getPayload().toString();
        String ussername = session.getId();
        System.out.println(ussername+":"+payload);

        // 수신된 메시지를 처리하는 코드를 작성합니다.

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // WebSocket 통신 중 에러가 발생하면 호출됩니다.
        System.out.println("웹소켓이 에러났당@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("웹소켓이 닫혔당@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        // WebSocket 연결이 닫히면 호출됩니다.


    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
