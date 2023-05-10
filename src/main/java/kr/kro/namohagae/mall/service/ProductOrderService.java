package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.Address;
import kr.kro.namohagae.mall.entity.ProductOrder;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductOrderService {
    private final ProductOrderDao productOrderDao;
    private final ProductOrderDetailDao productOrderDetailDao;
    private final CartDetailDao cartDetailDao;
    private final AddressDao addressDao;
    private final ProductDao productDao;

    public List<ProductOrderDetail> orderDetailList(List<Integer> list, Integer memberNo) {
        return cartDetailDao.findWithProduct(list, memberNo);
    }

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
