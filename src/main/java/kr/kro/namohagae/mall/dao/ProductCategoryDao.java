package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryDao {
    // 모든 카테고리 조회
    public List<ProductCategory> findAll();
}
