package kr.kro.namohagae.mall.entity;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Product {
    private Integer productNo;
    private Integer productCategoryNo;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private Integer productGrade;
    private String productContent;
    private LocalDateTime productWriteDate;
}
