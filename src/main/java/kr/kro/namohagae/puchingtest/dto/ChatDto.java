package kr.kro.namohagae.puchingtest.dto;

import kr.kro.namohagae.puchingtest.entity.Message;
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

    @Data
    public static class MessageSave{
        private Integer messageSender;
        private Integer messageReceiver;
        private String messageContent;
        private String messageContentType;
        private LocalDateTime messageWriteDate;

        public Message toEntity(Integer sender,Integer receiver,String messageContent,String messageContentType) {
            return Message.builder().messageSender(sender).messageReceiver(receiver).messageContent(messageContent)
                    .messageContentType(messageContentType).messageWriteDate(LocalDateTime.now()).build();
        }
    }

}
