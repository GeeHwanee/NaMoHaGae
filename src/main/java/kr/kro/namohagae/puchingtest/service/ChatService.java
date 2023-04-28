package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import kr.kro.namohagae.puchingtest.dto.MessageDto;
import kr.kro.namohagae.puchingtest.entity.ChatRoom;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatDao cdao;
    @Autowired
    private MemberDao mdao;

    public List<ChatRoomDto.Read> findAllChatRoom(Integer myMemberNo) {
        List<ChatRoomDto.Read> list = cdao.findAllChatRoom(myMemberNo);
        return list;
    }

    //나중에 파라미터 값을 멤버 넘버로 받도록 고치자
    public Integer saveTextMessage(String senderEmail, String receiverEmail, String messageContent,String messageType){
        Integer senderNo=mdao.findNoByUsername(senderEmail);
        Integer receiverNo=mdao.findNoByUsername(receiverEmail);
        Message message =  new MessageDto.MessageSave(senderNo,receiverNo,messageContent).toEntity(messageType);

        if(cdao.existsByChatRoom(senderNo,receiverNo)==false){

            cdao.saveChatRoom(senderNo,receiverNo);
            cdao.saveChatRoom(receiverNo,senderNo);     //첫 메세지시 채팅방 2개 생성 각자 채팅방이 생성
        }
        return cdao.saveMessage(message);
    }

    public List<MessageDto.MessageRead> findMessageLog(String senderEmail,Integer receiverNo){
            Integer senderNo = mdao.findNoByUsername(senderEmail);
        System.out.println(senderNo+"챗서비스");
        return  cdao.findAllMessageByReceiverNo(senderNo,receiverNo);
    }

}
