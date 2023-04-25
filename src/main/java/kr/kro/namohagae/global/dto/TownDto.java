package kr.kro.namohagae.global.dto;


import kr.kro.namohagae.global.entity.Town;
import lombok.Data;

import java.util.List;

public class TownDto {

    @Data
    public static class Read{
        private Integer townNo;
        private String townDong;
    }

}
