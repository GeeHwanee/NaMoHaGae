package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductOrderDetailDto {
    @Data
    public static class OrderDetailList {
        private String productImage;
        private String productName;
        private Integer productOrderDetailNo;
        private Integer productOrderDetailCount;
        private Integer productOrderDetailPrice;
        private Boolean productOrderDetailReviewEnabled;
    }
}
