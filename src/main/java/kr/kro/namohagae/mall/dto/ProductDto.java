package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {

    // 상품 등록
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Add {
        private Integer productCategoryNo;
        private String productName;
        private Integer productPrice;
        private Integer productStock;
        private String productContent;
        private List<MultipartFile> productImages;

        public Product toEntity() {
            return Product.builder().productCategoryNo(productCategoryNo).productName(productName).productPrice(productPrice).productStock(productStock).productGrade(0).productContent(productContent).productWriteDate(LocalDateTime.now()).build();
        }
    }

    @Data
    public static class Put{
        private Integer productNo;
        private String productName;
        private Integer productPrice;
        private Integer productStock;
        private String productContent;
        private List<MultipartFile> productImages;

        public Product toEntity(){
            return Product.builder().productNo(productNo).productName(productName).productPrice(productPrice).productStock(productStock).productContent(productContent).build();
        }

    }

    // 상품 목록
    @Data
    public static class ReadAll {
        private Integer productNo;
        private String productName;
        private Integer productPrice;
        private Integer productStock;
        private String productImage;
        //private Boolean favoriteEnabled; // 찜여부
    }

    // 상품 상세보기
    @Data
    public static class Read {
        private Integer productNo;
        private String productName;
        private String productContent;
        private Integer productPrice;
        private Integer productGrade; // 별점평균
        //private Integer productReviewTotalStar; // 별점합계
        //private Integer productReviewTotalCount; // 리뷰개수
        private Integer productStock;
        //private Boolean favoriteEnabled; // 찜여부
        private List<String> productImages;
        private List<ProductReviewDto.list> productReviews;
        private List<QnaDto.list> qnas;


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
        private Integer categoryNo;
        private List<ProductDto.ReadAll> products;
    }
}
