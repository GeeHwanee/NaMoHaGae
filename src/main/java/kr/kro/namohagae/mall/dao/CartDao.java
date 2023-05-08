package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.Cart;
import kr.kro.namohagae.mall.entity.CartDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartDao {
    // 장바구니에 저장
    public Integer save(Cart cart);

    // 해당 사용자의 장바구니 찾기
    public List<CartDetail> findByMemberNo(Integer memberNo);

    // 장바구니 총 가격 업데이트
    public Integer updateTotalPrice(Cart cart);

    // 장바구니에서 삭제
    public Integer delete(List<Integer> list, Integer memberNo);

    // 장바구니 번호로 장바구니 검색
    public Optional<Cart> findByCartNoAndMemberNo(Integer cartNo, Integer memberNo);
}
