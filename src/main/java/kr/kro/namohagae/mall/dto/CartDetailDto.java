package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDetailDto {
    @Data
    @AllArgsConstructor
    public static class Add {
        private Integer productNo;
        private String productImage;
        private String productName;
        private Integer cartDetailCount;
        private Integer cartDetailPrice;
        private Integer cartTotalPrice;
    }

    @Data
    @AllArgsConstructor
    public static class Read {
        private List<Add> items;
        private Integer cartTotalPrice;
    }

    @Data
    @AllArgsConstructor
    public static class Update {
        private Integer cartDetailCount;
        private Integer cartDetailPrice;
        private Integer cartTotalPrice;
    }

    @Data
    public static class Delete {
        private List<Integer> list;
    }

}
