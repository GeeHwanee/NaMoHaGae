package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.ProductOrderDetailDto;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductOrderDetailDao {
    // 주문 상세 저장
    public Integer save(ProductOrderDetail productOrderDetail);

    // 내가 쓴 리뷰
    public ProductOrderDetailDto.OrderInformation findByOrderDetailNo(Integer productOrderDetailNo);

    // 리뷰 작성 완료
    public Integer updateReview(Integer productOrderDetailNo);


}
