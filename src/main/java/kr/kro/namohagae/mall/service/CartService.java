package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.CartDao;
import kr.kro.namohagae.mall.dao.CartDetailDao;
import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dto.CartDetailDto;
import kr.kro.namohagae.mall.dto.CartDto;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Cart;
import kr.kro.namohagae.mall.entity.CartDetail;
import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.exception.ProductNotFoundException;
import kr.kro.namohagae.mall.exception.ProductStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartDao cartDao;
    private final CartDetailDao cartDetailDao;
    private final ProductDao productDao;


    // 장바구니에 상품 추가
    public String add(Integer productNo, Integer memberNo) {
        Product product = null;

        try {
            // 1. 추가하려는 상품 정보를 읽어온다(없으면 예외 발생)
            product = productDao.findProductByNo(productNo).orElseThrow(()->new ProductNotFoundException());

            // 2. 사용자의 장바구니에 지금 추가하려는 상품이 담겨있는 지 확인
            //    새로운 상품이면 NoSuchElementException 핸들러에서 처리
            CartDetail cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();
            // 3. 재고 확인
            if(cartDetail.getCartDetailCount() >= product.getProductStock()) {
                return product.getProductStock() + "개까지 구입할 수 있습니다";
            } else {
                cartDetail.setCartDetailCount(cartDetail.getCartDetailCount() + 1);
                cartDetail.setCartDetailPrice(product.getProductPrice());
                cartDetailDao.update(cartDetail);
                return "장바구니에 " + cartDetail.getCartDetailCount() + "개 담았습니다";
            }

        } catch(ProductNotFoundException e) {
            return "상품 정보를 찾을 수 없습니다";
        } catch(NoSuchElementException e) {
            Optional<Cart> cart = cartDao.findByMemberNo(memberNo);
            Integer cartNo;
            if (cart.isPresent()) {
                cartNo = cart.get().getCartNo();
            } else {
                Cart newCart = Cart.builder().memberNo(memberNo).cartTotalPrice(product.getProductPrice()).build();
                cartDao.save(newCart);
                cartNo = newCart.getCartNo();
            }
            cartDetailDao.save(CartDetail.builder().memberNo(memberNo).cartNo(cartNo).productNo(productNo).cartDetailCount(1).cartDetailPrice(product.getProductPrice()).build());
            return "장바구니에 1개 담았습니다";
        }
    }
    
    // 장바구니 목록 조회
    public CartDetailDto.Read list(Integer memberNo) {
        List<CartDetail> carts = cartDetailDao.findCartDetailsByMemberNo(memberNo);
        List<CartDetailDto.list> items = new ArrayList<>();
        int totalPrice = 0;
        for (CartDetail cartDetail : carts) {
            ProductDto.Read dto = productDao.findByProductNo(cartDetail.getProductNo());
            CartDetailDto.list item = new CartDetailDto.list(cartDetail.getProductNo(), dto.getProductImages().get(0), dto.getProductName(), dto.getProductStock(), cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), cartDetail.getCartDetailCount()*dto.getProductPrice());
            items.add(item);
            totalPrice += item.getCartTotalPrice();
        }
        return new CartDetailDto.Read(items, totalPrice);
    }


    // 장바구니에 담긴 상품 개수 증가
    public CartDetailDto.Update increase(Integer productNo, Integer memberNo) {
        Product product = productDao.findProductByNo(productNo).orElseThrow(()->new ProductNotFoundException());
        CartDetail cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();

        if(cartDetail.getCartDetailCount()>=product.getProductStock())
            throw new ProductStockException(product.getProductStock() + "개까지 구입할 수 있습니다");

        cartDetailDao.updateIncrease(cartDetail.getCartDetailNo());
        cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();

        return new CartDetailDto.Update(cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), (cartDetail.getCartDetailCount() * cartDetail.getCartDetailPrice()));
    }


    // 장바구니에 담긴 상품 개수 감소
    public CartDetailDto.Update decrease(Integer productNo, Integer memberNo) {
        CartDetail cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();
        if (cartDetail.getCartDetailCount() <= 1) {
            throw new ProductStockException("상품 수량에 0개를 입력하실 수 없습니다.");
        }

        cartDetailDao.updateDecrease(cartDetail.getCartDetailNo());
        cartDetail = cartDetailDao.findByMemberNoAndProductNo(memberNo, productNo).get();
        return new CartDetailDto.Update(cartDetail.getCartDetailCount(), cartDetail.getCartDetailPrice(), (cartDetail.getCartDetailCount() * cartDetail.getCartDetailPrice()));
    }

    // 장바구니에서 상품 삭제
    public Integer delete(CartDto.Delete dto, Integer memberNo) {
        return cartDao.delete(dto.getList(), memberNo);
    }
}
