package kr.kro.namohagae.global.dto;


import jdk.jfr.DataAmount;
import kr.kro.namohagae.global.entity.Town;
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
    @Data
    public static class save{
        private String townGu;
        private String townDong;
        private String townLongitude;
        private String townLatitude;

        public Town toEntity(Double latitude,Double longitude){
            return Town.builder().townGu(townGu).townDong(townDong).townLatitude(latitude).townLongitude(longitude).build();
        }
    }


}
