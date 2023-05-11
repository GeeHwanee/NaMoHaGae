package kr.kro.namohagae.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ProductOrder {
    private Integer productOrderNo;
    private Integer memberNo;
    private Integer addressNo;
    private Integer productOrderTotalPrice;
    private LocalDateTime productOrderDate;
}
