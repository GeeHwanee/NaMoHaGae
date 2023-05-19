package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
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

    // 상품 번호 찾아서 재고 업데이트
    //public void updateStockByProductNo(List<Integer> productNos, Integer productOrderDetailNo);
    public void updateStockByProductNo(Map<String, Object> params);

    // 상품 상세 보기에서 주문시 재고 업데이트
    public void updateStockByOrderFromProduct(Map<String, Object> params);

    // 상품 번호로 상품 정보 불러오기
    public Integer findInformationByProductNo(Integer productNo);

    // 상품 정보 읽어 오기
    public Optional<Product> findProductByNo(Integer productNo);

    // 상품명으로 검색
    public List<ProductDto.ReadAll> findByProductName(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo, String productName);

    // 필터(최신순) 검색
    public List<ProductDto.ReadAll> findAllByNewProduct(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(인기순) 검색
    public List<ProductDto.ReadAll> findAllByBestProduct(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(이름순) 검색
    public List<ProductDto.ReadAll> findAllByProductName(Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    public Integer delete(Integer productNo);

    public Integer update(Product product);
    
    // 리뷰 등록 시 상품 별점 업데이트
    public void updateProductGradebyReviewStar(Integer productNo);
}
