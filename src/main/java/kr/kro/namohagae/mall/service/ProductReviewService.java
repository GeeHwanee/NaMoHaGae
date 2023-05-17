package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.ProductOrderDetailDao;
import kr.kro.namohagae.mall.dao.ProductReviewDao;
import kr.kro.namohagae.mall.dto.ProductOrderDetailDto;
import kr.kro.namohagae.mall.dto.ProductReviewDto;
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
    public void write(ProductReviewDto.Write dto, Integer memberNo) {
        ProductReview productReview = dto.toEntity(memberNo);
        productReviewDao.save(productReview);
        productOrderDetailDao.updateReview(dto.getProductOrderDetailNo());
        //productDao.updateReview(productReview);
    }

    // 수정중 0517
    /*
    public ProductOrderDetail read(Integer productOrderDetailNo) {
        return productOrderDetailDao.findByOrderDetailNo(productOrderDetailNo);
    }
     */
    public ProductOrderDetailDto.OrderInformation read(Integer productOrderDetailNo) {
        return productOrderDetailDao.findByOrderDetailNo(productOrderDetailNo);
    }

    public ProductReviewDto.Pagination list(Integer pageNo, Integer productNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductReviewDto.ReviewList> review =  productReviewDao.findAllByProductNo(startRowNum, endRowNum, productNo);
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
        return new ProductReviewDto.Pagination(pageNo, prev, start, end, next, productNo, review);
    }

    public ProductReviewDto.PaginationMyReview myProductReviewList(Integer pageNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductReviewDto.MyReviewList> review =  productReviewDao.findAllByMemberNo(startRowNum,endRowNum,memberNo);
        System.out.println(review.get(0).getReviewContent());
        System.out.println(review.get(0).getReviewStar());
        Integer countOfReview = productReviewDao.countByMemberNo(memberNo);
        System.out.println(countOfReview);
        Integer countOfPage = (countOfReview-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductReviewDto.PaginationMyReview(pageNo, prev, start, end, next, review);
    }
}
