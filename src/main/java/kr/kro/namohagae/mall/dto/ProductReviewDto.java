package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.ProductReview;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ProductReviewDto {

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
        private List<ProductReview> reviews;
    }
    
    // 리뷰 쓰기
    @Data
    public static class Write {
        private String productName;
        private String productImage;
        //
        //private Integer productReviewNo;
        private Integer productNo;
        private Integer orderDetailNo;
        private String productReviewWriter;
        private String productReviewContent;
        private Integer productReviewStar;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime productReviewWriteDate;

        //public void setProductReviewNo(Integer productReviewNo) {
        //    this.productReviewNo = productReviewNo;
        //}

        public ProductReview toEntity(String memberEmail) {
            return ProductReview.builder().productNo(productNo).orderDetailNo(orderDetailNo).productReviewWriter(memberEmail).
                    productReviewContent(productReviewContent).productReviewStar(productReviewStar).productReviewWriteDate(productReviewWriteDate).build();
        }
    }

}
