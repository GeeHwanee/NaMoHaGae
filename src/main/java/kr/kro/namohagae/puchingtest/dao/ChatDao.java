package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import kr.kro.namohagae.puchingtest.dto.MessageDto;
import kr.kro.namohagae.puchingtest.entity.ChatRoom;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {


    public Boolean existsByChatRoom(Integer memberNo,Integer receiverNO);  //상대방과의 채팅방이 있는지 체크
    public void saveChatRoom(ChatRoom chatroom); // 첫 메세지 저장시 채팅방 생성

    public List<ChatRoomDto.Read> findAllChatRoom(Integer memberNo); // 자신이 가지고 있는 채팅방

    public void saveMessage(Message message); // 메세지 전송 성공시 저장

    public List<MessageDto.MessageRead> findAllMessage(Integer memberNo,Integer receiverNo); // 상대방과의 채팅 기록 불러오기



}
