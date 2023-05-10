package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteDto {

    @Data
    public static class list{
        private Integer productNo;
        private String productName;
        private Integer productPrice;
        private String productImage;
    }

    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<FavoriteDto.list> favorites;
    }
}
