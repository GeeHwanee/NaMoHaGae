package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.ProductReview;
import kr.kro.namohagae.mall.entity.Qna;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {

    // 상품 목록
    @Data
    public static class ReadAll {
        private Integer productNo;
        private String productName;
        private Integer productPrice;
        private String productImage;
    }

    // 상품 상세보기
    @Data
    public static class Read {
        private Integer productNo;
        private String productName;
        private String productContent;
        private Integer productPrice;
        private Integer productGrade;
        // 별점 평균 추가할 것 (별점합계/리뷰개수)
        private Integer productStock;
        private List<String> productImages;
        private List<ProductReview> productReviews;
        private List<Qna> qnas;
    }
    
    // 페이지네이션
}
