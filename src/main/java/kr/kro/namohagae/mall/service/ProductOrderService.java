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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class ProductOrderService {
    private final ProductOrderDao productOrderDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final CartDetailDao cartDetailDao;
    private final AddressDao addressDao;
    private final ProductDao productDao;

    // 결제할 주문 목록 조회
    public ProductOrderDto.Read orderReady(Integer memberNo, List<Integer> checkedProductNos) {
        // 선택한 상품만 장바구니에 담기
        List<CartDetail> carts = new ArrayList<>();
        for (Integer productNo : checkedProductNos) {
            CartDetail cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();
            if (cartDetail != null) {
                carts.add(cartDetail);
            }
        }

        // 주문 정보 저장을 위한 리스트 생성
        List<ProductOrderDto.list> orderItems = new ArrayList<>();
        int orderTotalPrice = 0;

        for (CartDetail cartDetail : carts) {
            ProductDto.Read dto = productDao.findByProductNo(cartDetail.getProductNo());
            ProductOrderDto.list orderItem = new ProductOrderDto.list(cartDetail.getProductNo(), dto.getProductImages().get(0), dto.getProductName(), cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), cartDetail.getCartDetailCount()*dto.getProductPrice());
            orderItems.add(orderItem);
            orderTotalPrice += orderItem.getOrderTotalPrice();
        }

        return new ProductOrderDto.Read(orderItems, orderTotalPrice);
    }


    // 멤버의 주소 찾기
    public List<AddressDto.Read> findAddress(Integer memberNo) {
        return addressDao.findAll(memberNo);
    }


    // 주문하기
    @Transactional
    public Integer placeOrder(List<ProductOrderDetail> items, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);

        // 총가격
        int orderTotalPrice = 0;
        for (ProductOrderDetail item : items) {
            orderTotalPrice += item.getProductOrderDetailPrice() * item.getProductOrderDetailCount();
        }

        // 주문 정보 저장
        ProductOrder productOrder = new ProductOrder(null, memberNo, address.getAddressNo(), orderTotalPrice, LocalDateTime.now());
        productOrderDao.save(productOrder);

        // 상품 주문 정보 저장 및 재고 처리
        List<Integer> productNos = new ArrayList<>();
        for (ProductOrderDetail item : items) {
            // 상품 주문 정보 저장
            productOrderDetailDao.save(item);

            // 상품 번호 추출
            Integer productNo = item.getProductNo();
            productNos.add(productNo);

            // 상품 재고 처리
            Integer productStock = productDao.findInformationByProductNo(productNo);
            if (productStock >= item.getProductOrderDetailCount()) {
//                productDao.updateStockByProductNo(productNos,item.getProductOrderDetailNo());
                Map<String, Object> params = new HashMap<>();
                params.put("productNo", productNo);
                params.put("productOrderDetailNo", item.getProductOrderDetailNo());
                System.out.println(productNo.toString() + "들어온 상품들");
                productDao.updateStockByProductNo(params);
            } else {
                throw new RuntimeException("재고가 부족합니다.");
            }
        }

        // 주문한 상품 제거
        if (!productNos.isEmpty()) {
            cartDetailDao.removeByCartNo(productNos, memberNo);
        }

        return productOrder.getProductOrderNo();
    }




    // 0512 아래부터 수정해야함


    public List<ProductOrderDto.Read> orderList(Integer memberNo) {
        return productOrderDao.list(memberNo);
    }


    public ProductOrderDto.Read findById(Integer orderNo) {
        return productOrderDao.read(orderNo);
    }

}