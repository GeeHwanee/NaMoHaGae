package kr.kro.namohagae.global.dto;


import jdk.jfr.DataAmount;
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
    @Data
    public static class userGuAndDong{
        private Integer townNo;
        private String townGu;
        private String townDong;
    }



}
