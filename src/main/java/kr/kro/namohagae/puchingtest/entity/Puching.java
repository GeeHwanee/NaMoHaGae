package kr.kro.namohagae.puchingtest.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Puching {

    private Integer puchingNo;
    private Integer messageNo;
    private LocalDateTime puchingCreatedDate;
    private String puchingStatus;
    private LocalDateTime puchingPromiseDate;
    private Double puchingLatitude;
    private Double puchingLongitude;
    private LocalDateTime puchingApplyDate;
    private LocalDateTime puchingDeadlineDate;

}
