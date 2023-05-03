package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductImageDao {
    // 상품 이미지 저장
    public void save(List<ProductImage> productImages);

    public Integer delete(Integer productNo);

    public void update(List<ProductImage> images);
}
