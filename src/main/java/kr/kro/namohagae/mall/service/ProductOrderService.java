package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.Address;
import kr.kro.namohagae.mall.entity.CartDetail;
import kr.kro.namohagae.mall.entity.ProductOrder;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductOrderService {
    private final ProductOrderDao productOrderDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final CartDetailDao cartDetailDao;
    private final AddressDao addressDao;
    private final ProductDao productDao;

    // 결제할 주문 목록 조회 (수정중 -> 장바구니 목록 조회랑 같게)
    /*
    public List<ProductOrderDetail> orderDetailList(List<Integer> list, Integer memberNo) {
        return cartDetailDao.findWithProduct(list, memberNo);
    }

     */

    
    // 수정중
    public ProductOrderDto.Read orderReady(Integer memberNo) {
        // 장바구니 정보 조회
        List<CartDetail> carts = cartDetailDao.findCartDetailsByMemberNo(memberNo);
        // 주문 정보 저장을 위한 리스트 생성
        List<ProductOrderDto.list> orderItems = new ArrayList<>();
        int orderTotalPrice = 0;

        for (CartDetail cartDetail : carts) {
            ProductDto.Read dto = productDao.findByProductNo(cartDetail.getProductNo());
            ProductOrderDto.list orderItem = new ProductOrderDto.list(cartDetail.getProductNo(), dto.getProductImages().get(0), dto.getProductName(), cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), cartDetail.getCartDetailCount()*dto.getProductPrice());
            orderItems.add(orderItem);
            orderTotalPrice += orderItem.getOrderTotalPrice();
//            // 장바구니에서 제품 삭제
//            cartDetailDao.delete(cartDetail.getCartDetailNo());
        }
//        // 주문 정보 저장
//        OrderDto.Create order = new OrderDto.Create(memberNo, orderTotalPrice, orderItems);
//        OrderDto.Read savedOrder = orderDao.save(order);
//        return savedOrder;

        return new ProductOrderDto.Read(orderItems, orderTotalPrice);
    }







    // 멤버의 주소 찾기
    public List<AddressDto.Read> findAddress(Integer memberNo) {
        return addressDao.findAll(memberNo);
    }


    // 주문하기 (만드는중)
    @Transactional
    public Integer checkOrderInformation(List<ProductOrderDetail> items, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);

        // 총가격 (첫번째 단가 * 첫번째 가격) <- 이거 나중에 처리할 때 주의할것,
        ProductOrder productOrder = new ProductOrder(null, memberNo, address.getAddressNo(),
                items.get(0).getProductOrderDetailPrice() * items.get(0).getProductOrderDetailCount(), LocalDateTime.now());
        productOrderDao.save(productOrder);
        items.forEach(item->{
            productOrderDetailDao.save(item, productOrder.getProductOrderNo(), memberNo);

            // 판매를 했으면 해당 상품 재고-1 처리해줘야지. <- 카트서비스에서 카트담은수량과 재고수량 비교했던거처럼 만들어, 재고-1 하는 메서드 만들어서 해결.
            // 이거 되는지 체크해야함
            productDao.updateOrder(item);
        });
        // 주문한 상품 제거
        cartDetailDao.removeByCartNo(items, memberNo);
        return productOrder.getProductOrderNo();
    }


    public List<ProductOrderDto.Read> orderList(Integer memberNo) {
        return productOrderDao.list(memberNo);
    }


    public ProductOrderDto.Read findById(Integer orderNo) {
        return productOrderDao.read(orderNo);
    }
}
