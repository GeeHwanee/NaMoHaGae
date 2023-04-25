package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
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

    public List<ChatRoomDto.Read> findByChatRoom(String userEmail) {
        Integer memberNo= mdao.findNoByUsername(userEmail);


        List<ChatRoomDto.Read> list = cdao.findAllChatRoom(memberNo);
        return list;
    }

    public Integer savemessage(Message message){

        return null;
    }

}
