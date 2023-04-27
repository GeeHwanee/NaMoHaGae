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

@RequiredArgsConstructor
@Service
public class ProductReviewService {
    private final ProductReviewDao productReviewDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final ProductDao productDao;

    @Transactional
    public void write(ProductReviewDto.Write dto, String memberEmail) {
        ProductReview productReview = dto.toEntity(memberEmail);
        productReviewDao.save(productReview);
        productOrderDetailDao.updateReview(dto.getOrderDetailNo());
        //productDao.updateReview(productReview);
    }

    public ProductOrderDetail read(Integer orderDetailNo) {
        return productOrderDetailDao.findByOrderDetailNo(orderDetailNo);
    }
}
