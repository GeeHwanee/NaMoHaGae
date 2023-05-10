package kr.kro.namohagae.mall.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.entity.ProductOrderDetail;
import kr.kro.namohagae.mall.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ProductOrderController {
    private final ProductOrderService service;

    // 장바구니 -> 주문 확인 화면 이동
    // 모델 체크
    @PostMapping("/mall/order")
    public String orderReady(ProductOrderDto.OrderList dto, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpSession session) {
        List<ProductOrderDetail> items = service.orderDetailList(dto.getList(), myUserDetails.getMemberNo());
        session.setAttribute("items", items);
        return "redirect:/mall/order/ready";
    }

    // 주문 확인
    @GetMapping("/mall/order/ready")
    public ModelAndView orderDetailList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        // 주문 정보를 세션에 저장하므로 어느정도 시간이 지난 다음 리프레시하면 내용이 없다...이 경우 /mall/cart/list로 이동시킨다
        if(session.getAttribute("items")==null)
            return new ModelAndView("redirect:/mall/cart/list");

        // 장바구니에서 주문 넣은 값들
        List<ProductOrderDetail> items = (List<ProductOrderDetail>)session.getAttribute("items");
        // 멤버의 주소값
        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("addresses", addresses);
        return new ModelAndView("/mall/order/ready").addObject("map", map);
    }
    
    // 주문하기
    @PostMapping("/order/check")
    public String checkOrderInformation(Integer addressNo, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpSession session, RedirectAttributes ra) {
        List<ProductOrderDetail> items =(List<ProductOrderDetail>)session.getAttribute("items");
        Integer orderNo = service.checkOrderInformation(items, addressNo, myUserDetails.getMemberNo());
        // 주문 결과는 1회성이므로 세션이 아니라 RedirectAttributes을 이용
        ra.addFlashAttribute("orderNo", orderNo);
        return "redirect:/mall/order/success";
    }

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
