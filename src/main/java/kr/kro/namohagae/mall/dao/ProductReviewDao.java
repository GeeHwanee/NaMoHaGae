package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.ProductReviewDto;
import kr.kro.namohagae.mall.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductReviewDao {
    // 리뷰 저장
    public Integer save(ProductReview productReview);

    // 특정 상품의 리뷰 개수를 조회
    public Integer count(Integer productNo);

    // 상품 리뷰 보기
    public List<ProductReview> findByProductNo(Integer productNo);
    
    // 특정 상품의 리뷰 목록 조회
    public List<ProductReviewDto.ReviewList> findAllByProductNo(Integer startRowNum, Integer endRowNum, Integer productNo);

    public Integer countByMemberNo(Integer memberNo);

    public List<ProductReviewDto.MyReviewList> findAllByMemberNo(String url, Integer startRowNum,Integer endRowNum,Integer memberNo);
}
