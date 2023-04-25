package kr.kro.namohagae.puchingtest.dto;

import kr.kro.namohagae.puchingtest.entity.ChatRoom;
import kr.kro.namohagae.puchingtest.entity.Message;
import lombok.Data;

import java.time.LocalDateTime;

public class ChatRoomDto {

    @Data
    public static class Read{
        private Integer chatRoomReceiverNo;
        private String memberNickName;
        private String memberimage;
    }
    @Data
    public static class Save{
        private Integer memberNo;
        private Integer receiverNo;


    }


}
