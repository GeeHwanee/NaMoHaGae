package kr.kro.namohagae.puching.dto;

import lombok.Data;

public class ChatRoomDto {

    @Data
    public static class Read{
        private Integer chatRoomReceiverNo;
        private String memberNickName;
        private String memberImage;
        private String memberEmail;
    }



}
