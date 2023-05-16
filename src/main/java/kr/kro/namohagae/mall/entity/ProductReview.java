package kr.kro.namohagae.mall.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductReview {
    private Integer productReviewNo;
    private Integer productNo;
    private Integer productOrderNo;
    private Integer productOrderDetailNo;
    private Integer productReviewWriter;
    private String productReviewContent;
    private Integer productReviewStar;
    private LocalDateTime productReviewWriteDate;
}
