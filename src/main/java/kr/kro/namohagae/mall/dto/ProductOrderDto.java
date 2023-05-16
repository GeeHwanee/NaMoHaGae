package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductImage;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
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
        //private Integer addressNo;
        private String addressPostcode;
        private String addressAddress;
        private String addressAddressDetail;
        // private String productImage;
        // 오더디테일디티오에서 상품명, 상품이미지,디테일리스트 끌고오기
        private List<ProductImage> productImageList;
        private List<Product> productList;
        private List<ProductOrderDetail> orderDetailList;
    }
}
