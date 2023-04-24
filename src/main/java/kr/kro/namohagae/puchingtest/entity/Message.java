package kr.kro.namohagae.puchingtest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

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
}
