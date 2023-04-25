package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatDao dao;

    public List<ChatRoomDto.Read> findByChatRoom(Integer memberNo) {
        List<ChatRoomDto.Read> list = dao.findAllChatRoom(memberNo);
        return list;
    }

    public Integer savemessage(Message message){

        return null;
    }

}
