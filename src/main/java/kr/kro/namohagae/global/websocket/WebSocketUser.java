package kr.kro.namohagae.global.websocket;

import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

@Getter
public class WebSocketUser {
    private final String username;
    private final List<WebSocketSession> list;

    // 생성(생성자)
    public WebSocketUser(String username, WebSocketSession session){
        this.username = username;
        list = new Vector<>();
        this.list.add(session);
    }

    public void add(WebSocketSession session){
        this.list.add(session);
    }

    public void remove(WebSocketSession session) {
        this.list.remove(session);
    }

    public void sendMessage(String message) {
        TextMessage textMessage = new TextMessage(message);
        for(WebSocketSession session:list) {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLastSession() {
        return list.size()<=1;
    }

}
