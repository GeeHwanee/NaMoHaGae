package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImage {
    private Integer productNo;
    private Integer productImageNo;
    private String productImageFilename;
}
