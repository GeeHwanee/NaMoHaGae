package kr.kro.namohagae.mall.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCategoryDto {
    @Data
    public static class ForList {
        private Integer productCategoryNo;
        private String productCategoryName;
    }
}
