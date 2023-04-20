package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartDetail {
    private Integer cartDetailNo;
    private Integer memberNo;
    private Integer cartNo;
    private Integer productNo;
    private Integer cartDetailCount;
    private Integer cartDetailPrice;
}
