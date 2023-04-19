package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {
    public Integer save(Product product);
}
