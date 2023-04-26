package kr.kro.namohagae.puchingtest.entity;

import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Message {
    private Integer messageNo;
    private Integer messageSender;
    private Integer messageReceiver;
    private String messageContent;
    private String messageContentType;
    private LocalDateTime messageWriteDate;

//        public ChatRoomDto.Read toReadDto(){
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("");
//
//            return ChatRoomDto.Read();
//        };
}
