package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.ChatDto;
import kr.kro.namohagae.puchingtest.entity.ChatRoom;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {

    public void saveChatRoom(ChatRoom chatroom);

    public List<ChatDto.ChatRoomRead> findByChatRoom(Integer memberNo);


    public void saveMessage(Message message);

    public List<Message> findAllMessage(Integer memberNo);



}
