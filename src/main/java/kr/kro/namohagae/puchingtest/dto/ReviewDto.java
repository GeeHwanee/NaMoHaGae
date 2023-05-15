package kr.kro.namohagae.puchingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

public class ReviewDto {

    @Data
    public static class profile {
        private String reviewWriter;
        private String reviewContent;
    }
    @Data
    public static class  Imformation{
        private Integer receiverNo;
        private String reviewReceiver;
        private String reviewContent;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class PaginationProfie {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<ReviewDto.profile> review;
    }
    @Data
    @ToString
    @AllArgsConstructor
    public static class PaginationImfo {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<ReviewDto.Imformation> review;
    }
}
