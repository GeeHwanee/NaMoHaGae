package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.ProductOrderDetailDao;
import kr.kro.namohagae.mall.dao.ProductReviewDao;
import kr.kro.namohagae.mall.dto.ProductReviewDto;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import kr.kro.namohagae.mall.entity.ProductReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductReviewService {
    private final ProductReviewDao productReviewDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final ProductDao productDao;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    @Transactional
    public void write(ProductReviewDto.Write dto, String memberEmail) {
        ProductReview productReview = dto.toEntity(memberEmail);
        productReviewDao.save(productReview);
        productOrderDetailDao.updateReview(dto.getOrderDetailNo());
        productDao.updateReview(productReview);
    }

    public ProductOrderDetail read(Integer orderDetailNo) {
        return productOrderDetailDao.findByOrderDetailNo(orderDetailNo);
    }

    public List<ProductReviewDto.Write> findInformationByProductNo(Integer productNo) {
        return productReviewDao.findInformationByProductNo(productNo);
    }

    public ProductReviewDto.Pagination list(Integer pageNo, Integer productNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductReview> reviews =  productReviewDao.findAllByProductNo(startRowNum, endRowNum, productNo);
        Integer countOfReview = productReviewDao.count(productNo);
        Integer countOfPage = (countOfReview-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductReviewDto.Pagination(pageNo, prev, start, end, next, productNo, reviews);
    }
}
