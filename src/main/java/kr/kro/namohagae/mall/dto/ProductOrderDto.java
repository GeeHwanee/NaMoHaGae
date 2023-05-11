package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductOrderDto {
    @Data
    public static class OrderList {
        List<Integer> list;
    }

    @Data
    public static class Read {
        private Integer productOrderNo;
        private Integer memberNo;
        private Integer addressNo;
        private Integer productOrderTotalPrice;
        private LocalDateTime productOrderDate;
        private List<ProductOrderDetail> orderDetailList;
    }
}
