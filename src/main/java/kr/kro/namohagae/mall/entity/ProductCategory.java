package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCategory {
    private Integer productCategoryNo;
    private String productCategoryName;
}
