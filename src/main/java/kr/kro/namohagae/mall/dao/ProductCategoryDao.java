package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductCategoryDao {
    // 카테고리 저장
    public Integer save(ProductCategory productCategory);
}
