package kr.kro.namohagae.puching.test.dao;

import kr.kro.namohagae.puching.dao.ChatDao;
import kr.kro.namohagae.puching.dto.MessageDto;
import kr.kro.namohagae.puching.entity.Message;
import kr.kro.namohagae.puching.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ChatTest {
    @Autowired
    private ChatDao dao;
    @Autowired
    private ChatService service;
    //@Test
    public void messageTest() {
        Message message=Message.builder().messageNo(1).messageSender(0)
                .messageReceiver(1).messageContentType("text").messageWriteDate(LocalDateTime.now()).messageContent("asdasd").build();
      Integer result=  dao.saveMessage(message);
      System.out.println(result);
        System.out.println("result2e2e12e12e21e21");
    }

   // @Test
    public void messagefindTest() {
        List<MessageDto.MessageRead> list= dao.findAllMessageByReceiverNo(1,0);
        for (MessageDto.MessageRead l:list) {

            System.out.println(l.getMessageSenderNo());
            System.out.println(l.getMessageReceiverNo());
            System.out.println(l.getMessageContent());
            System.out.println(l.getMessageContentType());
            System.out.println(l.getMessageWriteDate());
        }
    }
    @Test
    public void chatroomfind() {
        System.out.println(dao.existsByChatRoom(1,0));
    }

}
