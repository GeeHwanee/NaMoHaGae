package kr.kro.namohagae.puchingtest.dto;

import kr.kro.namohagae.global.util.constants.ImageConstants;
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
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageMessageSave{
        private Integer messageSender;
        private Integer messageReceiver;


        public Message toEntity(String messageContentType,String imageName){
            return Message.builder().messageSender(messageSender).messageReceiver(messageReceiver)
                    .messageContent("<img src='"+ImageConstants.IMAGE_CHAT_URL +imageName+"'alt='chatimage'>")
                    .messageContentType(messageContentType).messageWriteDate(LocalDateTime.now()).build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PuchingMessageSave{
        private String receiverUsername;
        private Double lat;
        private Double lng;
        private String day;
        private String time;
        private String address;
        public Message toEntity(Integer senderNo,Integer receiverNo,String messageContent){

            return Message.builder().messageSender(senderNo).messageReceiver(receiverNo).messageContent(messageContent)
                    .messageContentType("puching").messageWriteDate(LocalDateTime.now()).build();
        };
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PuchingMessageRead{
      private String content;
      private Integer MessageNo;
    };
}
