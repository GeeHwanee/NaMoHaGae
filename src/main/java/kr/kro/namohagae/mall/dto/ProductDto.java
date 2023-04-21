package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductReview;
import kr.kro.namohagae.mall.entity.Qna;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
        // 찜여부 추가
    }

    // 상품 상세보기
    @Data
    public static class Read {
        private Integer productNo;
        private String productName;
        private String productContent;
        private Integer productPrice;
        private Integer productGrade; // 별점평균
        private Integer productReviewTotalStar; // 별점합계
        private Integer productReviewTotalCount; // 리뷰개수
        private Integer productStock;
        // 찜여부 추가
        private List<String> productImages;
        private List<ProductReview> productReviews;
        private List<Qna> qnas;
    }

    // 상품 등록
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add {
        private String productName;
        private String productContent;
        private Integer productPrice;
        private Integer productStock;
        private Integer productCategoryNo;
        private List<MultipartFile> productImages;

        public Product toEntity() {
            return Product.builder().productName(productName).productContent(productContent).productPrice(productPrice).productStock(productStock).productCategoryNo(productCategoryNo).build();
        }
    }

    // 페이지네이션
    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private Integer category;
        private List<ProductDto.ReadAll> products;
    }
}