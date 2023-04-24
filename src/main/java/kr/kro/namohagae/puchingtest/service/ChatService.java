package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dto.ChatDto;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatDao dao;

    public List<ChatDto.ChatRoomRead> findByChatRoom(Integer memberNo) {
        List<ChatDto.ChatRoomRead> list = dao.findByChatRoom(memberNo);
        return list;
    }

    public Integer savemessage(Message message){

        return null;
    }

}
