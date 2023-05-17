package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.ProductReview;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductReviewDto {

    @Data
    public static class ReviewList {
        private Integer productReviewStar;
        private String productReviewContent;
        private Integer productReviewWriter;
        private LocalDateTime productReviewWriteDate;
    }
    
    // 페이지네이션
    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private Integer productNo;
        private List<ReviewList> review;
    }
    
    // 리뷰 쓰기
    @Data
    public static class Write {
        private Integer productNo;
        private Integer productOrderNo;
        private Integer productOrderDetailNo;
        private Integer productReviewWriter;
        private String productReviewContent;
        private Integer productReviewStar;

        public ProductReview toEntity(Integer memberNo) {
            return ProductReview.builder().productNo(productNo).productOrderNo(productOrderNo).productOrderDetailNo(productOrderDetailNo).productReviewWriter(memberNo).
                    productReviewContent(productReviewContent).productReviewStar(productReviewStar).productReviewWriteDate(LocalDateTime.now()).build();
        }
    }
    @Data
    public static class MyReviewList{
        private String reviewContent;
        private Integer reviewStar;
        private LocalDateTime reviewWriteDate;
        private Integer productNo;
        private String productImage;
        private String productName;
    }
    @Data
    @AllArgsConstructor
    public static class PaginationMyReview {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<MyReviewList> myReviewLists;
    }
}
