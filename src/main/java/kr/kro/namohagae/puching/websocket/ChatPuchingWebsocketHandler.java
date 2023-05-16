package kr.kro.namohagae.puching.websocket;

import kr.kro.namohagae.puching.service.ChatService;
import kr.kro.namohagae.puching.service.PuchingService;
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
    @Autowired
    private PuchingService puchingService;

    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username= session.getPrincipal().getName();
        session.getAttributes().put("username", username);
        sessions.put(username,session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String sendusername= session.getPrincipal().getName();
        String payload = message.getPayload().toString();

        JSONObject jsonPayload = new JSONObject(payload);
        String receiverUsername = jsonPayload.getString("receiverUsername");
        String messageContent = jsonPayload.getString("message");

        JSONObject responseJson = new JSONObject();
        responseJson.put("sendername", sendusername);
        responseJson.put("receivername", receiverUsername);
        responseJson.put("message", messageContent);

        if(messageContent.equals("cancel")){
            String content="<div class=\"puching-info\">\n" +
                    "<span class=\"puching-title\">퍼칭이 취소 되었습니다</span>\n" +
                    "</div>";
        //퍼칭 서비스로 상대번호와 내번호로 신청상태인 퍼칭 테이블을 조회하고 있으면 취소상태로 바꾸고 메세지번호를 리턴
           chatService.cancelPuchingMessage(sendusername,receiverUsername,content); //메세지 내용을 취소되었습니다 로 바꾸는거
        }

        if(messageContent.equals("accept")){
            chatService.aceeptPuchingMessage(sendusername,receiverUsername);
            //퍼칭 서비스로 상대번호와 내번호로 신청상태인 퍼칭테이블을 조회하고 있으면 수락상태로 바꾸고 퍼칭번호를 리턴 리뷰작성하러가기 페이지에는 모델에 퍼칭테이블 내용을 담아서쏜다
            //서브쿼리
        }


        if (receiverUsername != null) {
            // 수신자가 지정된 경우, 수신자에게만 메시지를 전송합니다.
            WebSocketSession receiverSession = sessions.get(receiverUsername);

            if (receiverSession != null && receiverSession.isOpen()) {
                TextMessage textMessage = new TextMessage(responseJson.toString());
                receiverSession.sendMessage(textMessage);
            }
        } else {
            // 수신자가 지정되지 않은 경우, 메시지를 전송하지 않습니다. 여기에 메세지전송 실패를 리턴해줘서 채팅창에 찍자
        }

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
