package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.CartDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartDetailDao {
    // 장바구니 상세 정보 저장
    public Integer save(CartDetail cartDetail);

    // 장바구니 상세 정보 업데이트
    public Integer update(CartDetail cartDetail);

    // 장바구니 상세 정보 삭제
    public Integer deleteByCartDetailNoAndCartNoAndMemberNo(Integer cartDetailNo, Integer cartNo, Integer memberNo);

    // 장바구니 번호로 장바구니 상세 정보 검색
    public List<CartDetail> findByCartNoAndMemberNo(Integer cartNo, Integer memberNo);
}
