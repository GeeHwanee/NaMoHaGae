package kr.kro.namohagae.puchingtest.websocket;


import kr.kro.namohagae.puchingtest.dto.MessageDto;
import kr.kro.namohagae.puchingtest.service.ChatService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatPuchingWebsocketHandler implements WebSocketHandler{

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
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        // 현재 상대와 퍼칭 중인 테이블이 있는지 여부
       //퍼칭 테이블에 저장 하고 상대방에게 띄울 수 있는 메세지
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("퍼칭웹소켓에러@@@@@@");
        System.out.println(exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      String username= session.getPrincipal().getName();
        sessions.remove(username);
        System.out.println(status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
