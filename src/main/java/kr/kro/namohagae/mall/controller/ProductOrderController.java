package kr.kro.namohagae.mall.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dao.CartDetailDao;
import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.CartDetail;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import kr.kro.namohagae.mall.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class ProductOrderController {
    private final ProductOrderService service;
    private final CartDetailDao cartDetailDao;
    private final ProductDao productDao;


    // 장바구니 -> 주문 확인 이동
    @PostMapping("/mall/order")
    public String orderReady(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam(value = "checkedProductNos") Integer[] checkedProductNos) {
        Integer memberNo = myUserDetails.getMemberNo();
        ProductOrderDto.Read order = service.orderReady(memberNo, Arrays.asList(checkedProductNos));

        session.setAttribute("checkedProductNos", checkedProductNos);
        session.setAttribute("orderItems", order.getOrderItems());
        session.setAttribute("orderTotalPrice", order.getOrderTotalPrice());

        return "redirect:/mall/order/ready";
    }


    // 주문 확인
    @GetMapping("/mall/order/ready")
    public ModelAndView orderDetailList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer[] checkedProductNos = (Integer[]) session.getAttribute("checkedProductNos");

        if (checkedProductNos == null) {
            return new ModelAndView("redirect:/mall/cart/list");
        }

        ProductOrderDto.Read order = service.orderReady(myUserDetails.getMemberNo(), Arrays.asList(checkedProductNos));
        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());

        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", order.getOrderItems());
        map.put("orderTotalPrice", order.getOrderTotalPrice());
        map.put("addresses", addresses);

        return new ModelAndView("/mall/order/ready").addObject("map", map);
    }


    // 주문하기
    @PostMapping("/order/check")
    public String placeOrder(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Integer addressNo) {
        Integer[] checkedProductNos = (Integer[]) session.getAttribute("checkedProductNos");

        // 선택한 상품들 조회
        List<CartDetail> cartDetails = cartDetailDao.findByMemberNoAndProductNos(myUserDetails.getMemberNo(), checkedProductNos);

        // 주문 상품 정보 생성
        List<ProductOrderDetail> items = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            ProductDto.Read product = productDao.findByProductNo(cartDetail.getProductNo());
            ProductOrderDetail item = new ProductOrderDetail(null, 2, cartDetail.getProductNo(), cartDetail.getCartDetailCount(),
                    product.getProductPrice(),true);
            items.add(item);
        }

        // 주문 정보 저장
        Integer productOrderNo = service.placeOrder(items, myUserDetails.getMemberNo(), addressNo);

        for (ProductOrderDetail item : items) {
            item.setProductOrderNo(productOrderNo);
        }

        session.removeAttribute("checkedProductNos");
        return "/mall/order/success";
    }


    
    // 0512 아래부터 수정해야함
    
    
    
    
    // 주문 결과 보기
    @GetMapping("/mall/order/success")
    public String orderSuccess(Model model, HttpServletRequest req, RedirectAttributes ra) {
        Map<String, ?> paramMap = RequestContextUtils.getInputFlashMap(req);
        if(paramMap!=null) {
            Integer orderNo = (Integer)paramMap.get("orderNo");
            model.addAttribute("order", service.findById(orderNo));
            return "/mall/order/success";
        } else {
            ra.addFlashAttribute("msg", "잘못된 작업입니다");
            return "redirect:/mall/main";
        }
    }

    // 주문 목록 보기
    @GetMapping("/member/order/list")
    public void orderList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        service.orderList(myUserDetails.getMemberNo()).forEach(a->System.out.println(a));
        model.addAttribute("orders", service.orderList(myUserDetails.getMemberNo()));
    }
}
