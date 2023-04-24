package kr.kro.namohagae.puchingtest.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class ChatDto {

    @Data
    public static class ChatRoomRead{
        private Integer chatRoomReceiverNo;
        private String memberNickName;
        private String memberimage;
    }

    @Data
    public static class MessageRead{
        private Integer messageNo;
        private Integer messageSender;
        private Integer messageReceiver;
        private String messageContent;
        private String messageContentType;
        private LocalDateTime messageWriteDate;
    }

}
