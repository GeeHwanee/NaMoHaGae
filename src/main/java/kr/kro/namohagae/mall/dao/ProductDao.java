package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import kr.kro.namohagae.mall.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductDao {
    // 상품 정보 저장
    public Integer save(Product product);

    // 특정 카테고리의 상품 개수를 조회
    public Integer count(Integer categoryNo);

    // 상품 목록 조회
    public List<ProductDto.ReadAll> findAll(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 상품 상세 보기
    public ProductDto.Read findByProductNo(Integer productNo);

    // 상품 정보 읽어 오기
    public Optional<Product> findProductByNo(Integer productNo);

    // 상품명으로 검색
    public List<Product> findByProductName(String productName);

    // 필터(최신순) 검색
    public List<ProductDto.ReadAll> findAllByLatestOrder(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(판매량) 검색
    public List<ProductDto.ReadAll> findAllByOrderOfHighSales(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(이름순) 검색
    public List<ProductDto.ReadAll> findAllByProductName(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    public Integer updateReview(ProductReview productReview);

    public Integer updateOrder(ProductOrderDetail productOrderDetail);

    public Integer delete(Integer productNo);

    public Integer update(Product product);
}
