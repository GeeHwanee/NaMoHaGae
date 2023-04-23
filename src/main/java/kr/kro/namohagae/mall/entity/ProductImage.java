package kr.kro.namohagae.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private Integer productNo;
    private Integer productImageNo;
    private String productImageFilename;
}
