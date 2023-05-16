package kr.kro.namohagae.mall.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductOrderDto {
    @Data
    @AllArgsConstructor
    public static class OrderList {
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
        private List<OrderList> orderItems;
        private Integer orderTotalPrice;
    }

    @Data
    public static class OrderResult {
        private Integer productOrderNo;
        private Integer productOrderTotalPrice;
        private LocalDateTime productOrderDate;
        private Integer memberNo;
        private String addressPostcode;
        private String addressAddress;
        private String addressAddressDetail;
        private List<ProductOrderDetailDto.OrderDetailList> orderDetailList;
    }
}
