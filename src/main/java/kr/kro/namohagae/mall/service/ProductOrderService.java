package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.Address;
import kr.kro.namohagae.mall.entity.CartDetail;
import kr.kro.namohagae.mall.entity.ProductOrder;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final MemberDao memberDao;
    private Integer PAGESIZE = 10;
    private Integer BLOCKSIZE = 5;


    public ProductOrderDto.Read orderReady(Integer memberNo, List<Integer> checkedProductNos, Integer productOrderDetailCount) {
        List<ProductOrderDto.OrderList> orderItems = new ArrayList<>();
        Integer orderTotalPrice = 0;
        Integer memberPoint = memberDao.findMemberPointByMemberNo(memberNo);
        for (Integer productNo : checkedProductNos) {
            Optional<CartDetail> result = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo);
            CartDetail cartDetail;
            ProductDto.Read product = productDao.findByProductNo(ImageConstants.IMAGE_PRODUCT_URL,productNo);
            if (result.isPresent()) {
                cartDetail = result.get();
            } else{
                cartDetail = new CartDetail(null,memberNo,null,productNo,productOrderDetailCount,product.getProductPrice());
            }
                ProductOrderDto.OrderList orderItem = new ProductOrderDto.OrderList(cartDetail.getProductNo(), product.getProductImages().get(0), product.getProductName(), memberPoint, cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), cartDetail.getCartDetailCount()*product.getProductPrice());
                orderItems.add(orderItem);
                memberPoint = orderItem.getMemberPoint();
                orderTotalPrice += orderItem.getOrderTotalPrice();
        }

        return new ProductOrderDto.Read(orderItems, memberPoint, orderTotalPrice);
    }


    // 멤버의 주소 찾기
    public List<AddressDto.Read> findAddress(Integer memberNo) {
        return addressDao.findAll(memberNo);
    }

    public List<ProductOrderDto.OrderResult> orderList(Integer memberNo) {
        return productOrderDao.list(ImageConstants.IMAGE_PRODUCT_URL,memberNo);
    }

    public ProductOrderDto.OrderResult findById(Integer orderNo) {
        return productOrderDao.read(ImageConstants.IMAGE_PRODUCT_URL, orderNo);
    }

    public Integer saveOrder(List<ProductOrderDto.OrderList> orderItems, Integer orderTotalPrice, Integer memberNo, Integer addressNo, Integer usedMemberPoint) {
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

        // 현재 포인트
        memberDao.findMemberPointByMemberNo(memberNo);

        // 포인트 사용 후 멤버 포인트 차감
        if(usedMemberPoint!=null) {
            memberDao.updateMemberPointByMemberNo(usedMemberPoint, memberNo);
        }

        // 구매 포인트 적립
        Integer deliveryFee = 3000;
        Integer bonePoint = (int) ((productOrder.getProductOrderTotalPrice()-deliveryFee) * 0.01);
        productOrderDao.updateMemberPoint(bonePoint, memberNo);
        return productOrder.getProductOrderNo();
    }

    public ProductOrderDto.PaginationOrder listMyOrder(Integer pageNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductOrderDto.MyOrderList> orderLists = productOrderDao.myOrderList(ImageConstants.IMAGE_PRODUCT_URL,startRowNum,endRowNum,memberNo);
        System.out.println(orderLists.size());
        Integer countOfQna = productOrderDao.countMe(memberNo);
        Integer countOfPage = (countOfQna-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductOrderDto.PaginationOrder(pageNo,prev,start,end,next,orderLists);
    }
}