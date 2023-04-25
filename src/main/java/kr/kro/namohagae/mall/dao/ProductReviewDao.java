package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductReviewDao {
    // 리뷰 저장
    public Integer save(ProductReview productReview);
}
