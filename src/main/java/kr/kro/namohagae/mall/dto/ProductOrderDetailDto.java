package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductOrderDetailDto {
    @Data
    public static class OrderDetailList {
        private Integer productNo;
        private String productImage;
        private String productName;
        private Integer productOrderDetailNo;
        private Integer productOrderDetailCount;
        private Integer productOrderDetailPrice;
        private Boolean productOrderDetailReviewEnabled;
    }

    @Data
    public static class OrderInformation {
        private Integer productNo;
        private String productName;
        private String productImage;
        private Integer productOrderNo;
        private Integer productOrderDetailNo;
        private Boolean productOrderDetailReviewEnabled;
    }
}
