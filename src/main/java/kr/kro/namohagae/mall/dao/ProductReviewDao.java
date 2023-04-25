package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductReviewDao {
    // 리뷰 저장
    public Integer save(ProductReview productReview);

    // 상품 리뷰 보기
    public List<ProductReview> findByProductNo(Integer productNo);
}
