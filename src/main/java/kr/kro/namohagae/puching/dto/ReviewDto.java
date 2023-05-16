package kr.kro.namohagae.puching.dto;

import kr.kro.namohagae.puching.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @Data
    public static class profile {
        private String reviewWriter;
        private String reviewContent;
    }

    @Data
    public static class Information {
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
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class writeview {
        private String myImage;
        private String receiverImage;
        private String myNickName;
        private String receiverNickName;
        private Double latitude;
        private Double longitude;
        private String promiseDate;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class write {
        private Integer receiverNo;
        private Integer puchingNo;
        private String content;
        private Integer starPoint;

        public Review toEntity(Integer senderNo) {

            return Review.builder().puchingNo(puchingNo).puchingReviewContent(content).puchingReviewReceiver(receiverNo)
                    .puchingReviewWriter(senderNo).puchingReviewWriteDate(LocalDateTime.now()).puchingReviewStar((starPoint * 2) - 6).build();
        }

    }

        @ToString
        @AllArgsConstructor
        @Data
        public static class PaginationInfo {
            private Integer pageno;
            private Integer prev;
            private Integer start;
            private Integer end;
            private Integer next;
            private List<ReviewDto.Information> review;
        }
    }
