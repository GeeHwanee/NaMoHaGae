package kr.kro.namohagae.global.dto;


import lombok.Data;

public class TownDto {

    @Data
    public static class Read{
        private Integer townNo;
        private String townDong;
    }
    @Data
    public static class Gu{
        private String townGu;
    }

}
