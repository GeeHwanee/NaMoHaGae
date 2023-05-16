package kr.kro.namohagae.puching.entity;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
