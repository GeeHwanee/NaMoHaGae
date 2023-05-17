package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductOrderDao {
    // 주문 저장
    public Integer save(ProductOrder productOrder);

    // 주문 목록 보기
    public List<ProductOrderDto.OrderResult> list(Integer memberNo);

    // 주문 상세 보기
    public ProductOrderDto.OrderResult read(Integer productOrderNo);

    // 주문 완료시 멤버 포인트 지급
    public void updateMemberPoint(Integer bonePoint,Integer memberNo);
    public Integer countMe(Integer memberNo);

    public List<ProductOrderDto.MyOrderList> myOrderList(Integer startRowNum, Integer endRowNum, Integer memberNo);


}
