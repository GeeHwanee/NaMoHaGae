package kr.kro.namohagae.puchingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//@Getter
public class PuchingDto {

    @Data
    public static class read{
        private Integer townNo;
        private String townDong;
        private Double townLatitude;
        private Double townLongitude;
    };
};
