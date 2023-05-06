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
    public List<ProductReviewDto.list> findAllByProductNo(Integer startRowNum, Integer endRowNum,Integer productNo);

    // 리뷰 쓴 상품 찾기
    public List<ProductReviewDto.Write> findInformationByProductNo(Integer productNo);
}
