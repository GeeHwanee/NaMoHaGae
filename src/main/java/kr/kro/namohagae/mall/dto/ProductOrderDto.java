package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductOrderDto {
    @Data
    @AllArgsConstructor
    public static class list {
        private Integer productNo;
        private String productImage;
        private String productName;
        private Integer cartDetailCount;
        private Integer cartDetailPrice;
        private Integer orderTotalPrice;
    }

    @Data
    @AllArgsConstructor
    public static class Read {
        private List<ProductOrderDto.list> orderItems;
        private Integer orderTotalPrice;
    }
}
