package kr.kro.namohagae.puching.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Review {
    private Integer puchingReviewNo;
    private Integer puchingNo;
    private Integer puchingReviewWriter;
    private Integer puchingReviewReceiver;
    private String puchingReviewContent;
    private Integer puchingReviewStar;
    private LocalDateTime puchingReviewWriteDate;
}
