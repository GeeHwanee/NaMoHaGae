package kr.kro.namohagae.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductOrderDetail {
    private Integer productOrderDetailNo;
    private Integer productOrderNo;
    private Integer productNo;
    private Integer productOrderDetailCount;
    private Integer productOrderDetailPrice;
    private Boolean productOrderDetailReviewEnabled;

    public void setProductOrderNo(Integer productOrderNo) {
    }
}
