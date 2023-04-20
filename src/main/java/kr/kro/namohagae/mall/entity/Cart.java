package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cart {
    private Integer cartNo;
    private Integer memberNo;
    private Integer cartTotalPrice;
}
