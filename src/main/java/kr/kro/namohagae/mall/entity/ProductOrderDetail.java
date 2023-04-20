package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOrderDetail {
    private Integer productOrderDetailNo;
    private Integer productOrderNo;
    private Integer productNo;
    private Integer productOrderDetailCount;
    private Integer productOrderDetailPrice;
    private Boolean productOrderDetailReviewEnabled;
}
