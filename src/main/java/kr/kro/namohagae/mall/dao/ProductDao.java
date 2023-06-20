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

    // 상품명 검색시 상품 개수를 조회
    public Integer countByProductName(Integer categoryNo, String productName);

    // 상품 목록 조회
    public List<ProductDto.ReadAll> findAll(String url,Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 상품 상세 보기
    public ProductDto.Read findByProductNo(String url, Integer productNo);

    // 상품 번호 찾아서 재고 업데이트
    public void updateStockByProductNo(Map<String, Object> params);

    // 상품 번호로 상품 정보 불러오기
    public Integer findInformationByProductNo(Integer productNo);

    // 상품 정보 읽어 오기
    public Optional<Product> findProductByNo(Integer productNo);

    // 상품명으로 검색
    public List<ProductDto.ReadAll> findByProductName(String url,Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo, String productName);

    // 필터(최신순) 검색
    public List<ProductDto.ReadAll> findAllByNewProduct(String url, Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(인기순) 검색
    public List<ProductDto.ReadAll> findAllByBestProduct(String url, Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    // 필터(이름순) 검색
    public List<ProductDto.ReadAll> findAllByProductName(String url, Integer startRowNum, Integer endRowNum, Integer categoryNo, Integer memberNo);

    public Integer delete(Integer productNo);

    public Integer update(Product product);
    
    // 리뷰 등록 시 상품 별점 업데이트
    public void updateProductGradebyReviewStar(Integer productNo);

    public List<ProductDto.ReadAll> findAllByNewProductForMain(String url, Integer memberNo);

    public List<ProductDto.ReadAll> findAllByBestProductForMain(String url, Integer memberNo);

    public List<ProductDto.MdRecommend> findByRecommendEnabled(String url);
}
