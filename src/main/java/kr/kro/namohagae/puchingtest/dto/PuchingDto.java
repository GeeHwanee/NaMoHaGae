package kr.kro.namohagae.puchingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//@Getter
public class PuchingDto {

    @Data
    public static class readTown{
        private Integer townNo;
        private String townDong;
        private Double townLatitude;
        private Double townLongitude;
        private Integer townCnt;
    };

    @Data
    public static class readUser{
        private Integer memberNo;
        private String memberNickName;
        private Double memberLatitude;
        private Double memberLongitude;
        private Double distance;
    }
};
