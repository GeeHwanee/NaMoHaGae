package kr.kro.namohagae.puchingtest.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class ChatRoom {
    private Integer chatRoomNo;
    private Integer memberNo;
    private Integer chatRoomReceiverNo;



}
