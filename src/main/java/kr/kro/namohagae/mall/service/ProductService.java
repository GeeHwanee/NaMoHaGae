package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductCategoryDao productCategoryDao;
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;
    private final ProductReviewDao productReviewDao;
    private final QnaDao qnaDao;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }
    
    // 이미지 처리 후 만들것
    //public Integer add(ProductDto.Add dto) {  }

    public ProductDto.Pagination list(Integer pageNo, Integer categoryNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products =  productDao.findAll(startRowNum, endRowNum, categoryNo);
        Integer countOfProduct = productDao.count(categoryNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductDto.Pagination(pageNo, prev, start, end, next, categoryNo, products);
    }

//    public ProductDto.Read read(Integer productNo) {
//        ProductDto.Read dto = productDao.findByProductNo(productNo);
//        dto.setProductReviews(productReviewDao.findByProductNo(productNo));
//        dto.setQnas(qnaDao.findByProductNo(productNo));
//        return dto;
//    }

    public ProductDto.Read read(Integer productNo) {
        return productDao.findByProductNo(productNo);
    }


}
