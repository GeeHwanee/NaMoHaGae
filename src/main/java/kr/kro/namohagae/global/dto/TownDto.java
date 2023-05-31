package kr.kro.namohagae.global.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.DataAmount;
import kr.kro.namohagae.global.entity.Town;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class TownDto {

    @Data
    public static class Read{
        private Integer townNo;
        private String townDong;
    }
    @Data
    public static class Dong{
        private String townDong;
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
    @Data
    public static class FindAll{
        private Integer townNo;
        private String townGu;
        private String townDong;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<TownDto.FindAll> towns;
    }
}
