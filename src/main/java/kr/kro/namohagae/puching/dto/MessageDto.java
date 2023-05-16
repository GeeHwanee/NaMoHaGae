package kr.kro.namohagae.puching.dto;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.puching.entity.Message;
import kr.kro.namohagae.puching.entity.Puching;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        public Puching toEntity(Integer messageNo){
            String dateTimeStr = day + " " + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime puchingPromiseDate = LocalDateTime.parse(dateTimeStr, formatter);
            return Puching.builder().messageNo(messageNo).puchingLatitude(lat).puchingLongitude(lng).puchingStatus("신청")
                    .puchingApplyDate(LocalDateTime.now()).puchingCreatedDate(LocalDateTime.now()).puchingDeadlineDate(LocalDateTime.now())
                    .puchingPromiseDate(puchingPromiseDate).build();
        };
    }

}
