package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ProductOrderService {
    private final ProductOrderDao productOrderDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final CartDetailDao cartDetailDao;
    private final AddressDao addressDao;
    private final ProductDao productDao;

    // 결제할 주문 목록 조회(장바구니)
    public ProductOrderDto.Read orderReadyFromCart(Integer memberNo, List<Integer> checkedProductNos) {
        // 선택한 상품만 장바구니에 담기
        List<ProductOrderDto.OrderList> orderItems = new ArrayList<>();
        Integer orderTotalPrice = 0;
        for (Integer productNo : checkedProductNos) {
            Optional<CartDetail> result = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo);
            CartDetail cartDetail;
            ProductDto.Read product = productDao.findByProductNo(productNo);
            if (result.isPresent()) {
                cartDetail = result.get();
            } else{
                cartDetail = new CartDetail(null,memberNo,null,productNo,1,product.getProductPrice());
            }
                ProductOrderDto.OrderList orderItem = new ProductOrderDto.OrderList(cartDetail.getProductNo(), product.getProductImages().get(0), product.getProductName(), cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), cartDetail.getCartDetailCount()*product.getProductPrice());
                orderItems.add(orderItem);
                orderTotalPrice += orderItem.getOrderTotalPrice();
        }

        return new ProductOrderDto.Read(orderItems, orderTotalPrice);
    }


    // 결제할 주문 목록 조회(상품페이지)
    public ProductOrderDto.Read orderReadyFromProduct(Integer memberNo, Integer productNo) {
        ProductDto.Read dto = productDao.findByProductNo(productNo);
        ProductOrderDto.OrderList orderItem = new ProductOrderDto.OrderList(productNo, dto.getProductImages().get(0), dto.getProductName(), 1, dto.getProductPrice(), dto.getProductPrice());
        List<ProductOrderDto.OrderList> orderItems = Collections.singletonList(orderItem);
        Integer orderTotalPrice = orderItem.getOrderTotalPrice();

        System.out.println(orderItems + "오더저장되었나");

        return new ProductOrderDto.Read(orderItems, orderTotalPrice);
    }


    // 멤버의 주소 찾기
    public List<AddressDto.Read> findAddress(Integer memberNo) {
        return addressDao.findAll(memberNo);
    }

    /*
    // 주문하기(장바구니) 수정전
    @Transactional
    public Integer placeOrderFromCart(List<ProductOrderDetail> items, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);

        Integer orderTotalPrice = 0;
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
     */

    // 주문하기(장바구니) 수정중1
    @Transactional
    public Integer placeOrderFromCart(List<ProductOrderDetail> items, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);

        Integer orderTotalPrice = items.stream()
                .mapToInt(item -> item.getProductOrderDetailPrice() * item.getProductOrderDetailCount())
                .sum();

        // 주문 정보 저장
        ProductOrder productOrder = new ProductOrder(null, memberNo, address.getAddressNo(), orderTotalPrice, LocalDateTime.now());
        productOrderDao.save(productOrder);
        Integer productOrderNo = productOrder.getProductOrderNo();
        System.out.println(productOrder.getProductOrderNo() + "order 보여줘");

        // 상품 주문 정보 저장 및 재고 처리
        List<Integer> productNos = new ArrayList<>();
        for (ProductOrderDetail item : items) {
            // 상품 주문 정보 저장
            item.setProductOrderNo(productOrderNo);
            //ProductOrderDetail.builder().productOrderNo(productOrderNo).build();
            System.out.println(item.getProductOrderNo() + "저장 order 보여줘");
            productOrderDetailDao.save(item);

            // 상품 번호 추출
            Integer productNo = item.getProductNo();
            productNos.add(productNo);

            // 상품 재고 처리
            Integer productStock = productDao.findInformationByProductNo(productNo);
            if (productStock >= item.getProductOrderDetailCount()) {
                Map<String, Object> params = new HashMap<>();
                params.put("productNo", productNo);
                params.put("productOrderDetailNo", item.getProductOrderDetailNo());
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


    // 주문하기(상품페이지)
    @Transactional
    public Integer placeOrderFromProduct(Integer productNo, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);
        if (address == null) {
            throw new RuntimeException("주소를 찾을 수 없습니다.");
        }

        // 상품 정보 조회
        ProductDto.Read dto = productDao.findByProductNo(productNo);

        // 주문 상품 정보 생성
        ProductOrderDto.OrderList orderItem = new ProductOrderDto.OrderList(productNo, dto.getProductImages().get(0), dto.getProductName(), 1, dto.getProductPrice(), dto.getProductPrice());
        //List<ProductOrderDto.list> orderItems = Collections.singletonList(orderItem);
        //Integer orderTotalPrice = dto.getProductPrice();

        // 주문 정보 저장
        ProductOrder productOrder = new ProductOrder(null, memberNo, address.getAddressNo(), dto.getProductPrice(), LocalDateTime.now());
        productOrderDao.save(productOrder);

        // 상품 주문 정보 저장 및 재고 처리
        ProductOrderDetail item =
                new ProductOrderDetail(null, productOrder.getProductOrderNo(), productNo, orderItem.getCartDetailCount(), dto.getProductPrice(), true);
        productOrderDetailDao.save(item);

        Integer productStock = productDao.findInformationByProductNo(productNo);
        if (productStock >= item.getProductOrderDetailCount()) {
//            productDao.updateStockByOrderFromProduct(productNo, item.getProductOrderDetailNo());
            Map<String, Object> params = new HashMap<>();
            params.put("productNo", productNo);
            params.put("productOrderDetailNo", item.getProductOrderDetailNo());
            productDao.updateStockByOrderFromProduct(params);
        } else {
            throw new RuntimeException("재고가 부족합니다.");
        }

        return productOrder.getProductOrderNo();
    }





    // 아래부터 수정해야함 (dto 수정했고, 매퍼 수정)

    public List<ProductOrderDto.OrderResult> orderList(Integer memberNo) {
        return productOrderDao.list(memberNo);
    }


    public ProductOrderDto.OrderResult findById(Integer orderNo) {
        return productOrderDao.read(orderNo);
    }

    public Integer saveOrder(List<ProductOrderDto.OrderList> orderItems, Integer orderTotalPrice, Integer memberNo, Integer addressNo) {
        Address address = addressDao.findByMemberNoAndAddressNo(memberNo, addressNo);
        ProductOrder productOrder = new ProductOrder(null, memberNo, address.getAddressNo(), orderTotalPrice, LocalDateTime.now());
        productOrderDao.save(productOrder);
        Integer productOrderNo = productOrder.getProductOrderNo();
        List<Integer> productNos = new ArrayList<>();
        for (ProductOrderDto.OrderList item:orderItems) {
            ProductOrderDetail productOrderDetail = ProductOrderDetail.builder().productOrderNo(productOrderNo).productNo(item.getProductNo()).productOrderDetailCount(item.getCartDetailCount()).productOrderDetailPrice(item.getCartDetailPrice()).build();
            productOrderDetailDao.save(productOrderDetail);

            // 상품 번호 추출
            Integer productNo = item.getProductNo();
            productNos.add(productNo);

            // 상품 재고 처리
            Integer productStock = productDao.findInformationByProductNo(productNo);
            if (productStock >= item.getCartDetailCount()) {
                Map<String, Object> params = new HashMap<>();
                params.put("productNo", productNo);
                params.put("productOrderDetailNo", productOrderDetail.getProductOrderDetailNo());
                productDao.updateStockByProductNo(params);
            } else {
                throw new RuntimeException("재고가 부족합니다.");
            }
        }
        if (!productNos.isEmpty()) {
            cartDetailDao.removeByCartNo(productNos, memberNo);
        }
        return productOrder.getProductOrderNo();

    }
}