package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.CartDetail;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartDetailDao {
    // 장바구니 상세 정보 저장
    public Integer save(CartDetail cartDetail);

    // 장바구니 개수 증가 업데이트
    public Integer updateIncrease(Integer cartDetailNo);

    // 장바구니 개수 감소 업데이트
    public Integer updateDecrease(Integer cartDetailNo);

    // 장바구니 상세 정보 업데이트
    public Integer update(CartDetail cartDetail);

    // 장바구니 상세 정보 삭제
    public Integer delete(Integer cartDetailNo, Integer cartNo, Integer memberNo);

    // 장바구니 번호로 장바구니 상세 정보 검색
    public List<CartDetail> findByCartNoAndMemberNo(Integer cartNo, Integer memberNo);

    // 해당 사용자의 장바구니 상품 확인
    public Optional<CartDetail> findByMemberNoAndProductNo(Integer memberNo, Integer productNo);

    // 주문 완료 후 장바구니에서 상품 삭제
    public void removeByCartNo(List<Integer> productNos, Integer memberNo);

    // 사용자의 장바구니 상품 총 개수 검색
    public Integer findCountByMemberNo(Integer memberNo);

    // 사용자의 장바구니 상품 목록 검색
    public List<CartDetail> findCartDetailsByMemberNo(Integer memberNo);

    // 사용자의 장바구니 중 선택 상품 검색
    public List<CartDetail> findByMemberNoAndProductNos(Integer memberNo, List<Integer> productNos);

    // 사용자의 장바구니 상품 총 가격 검색
    public Integer findTotalPriceByMemberNo(Integer memberNo);

    // 결제할 상품 정보 불러오기
    public List<ProductOrderDetail> findWithProduct(List<Integer> list, Integer memberNo);
}
