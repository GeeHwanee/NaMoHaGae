package kr.kro.namohagae.puchingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

public class ReviewDto {

    @Data
    public static class profile {
        private String reviewReceiver;
        private String reviewContent;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<ReviewDto.profile> review;
    }
}
