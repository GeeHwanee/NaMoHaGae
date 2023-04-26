package kr.kro.namohagae.puchingtest.dto;

import kr.kro.namohagae.puchingtest.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MessageDto {


    @Data
    public static class MessageRead{
        private Integer messageSenderNo;
        private Integer messageReceiverNo;
        private String messageContent;
        private String messageContentType;
        private String messageWriteDate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageSave{
        private Integer messageSender;
        private Integer messageReceiver;
        private String messageContent;


        public Message toEntity(String messageContentType) {
            return Message.builder().messageSender(messageSender).messageReceiver(messageReceiver).messageContent(messageContent)
                    .messageContentType(messageContentType).messageWriteDate(LocalDateTime.now()).build();
        }
    }
}
