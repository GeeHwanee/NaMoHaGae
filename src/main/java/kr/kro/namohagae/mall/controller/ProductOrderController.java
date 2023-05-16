package kr.kro.namohagae.mall.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ProductOrderController {
    private final ProductOrderService service;


    @PostMapping("/mall/order")
    public String order(HttpSession session, @RequestParam(value = "checkedProductNos", required = false) List<Integer> checkedProductNos) {
        if (checkedProductNos != null) {
            session.setAttribute("checkedProductNos", checkedProductNos);
        } else {
            return "redirect:/mall/main";
        }
        return "redirect:/mall/order/ready";
    }

    @GetMapping("/mall/order/ready")
    public ModelAndView orderList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<Integer> checkedProductNos = (List<Integer>)session.getAttribute("checkedProductNos");
        session.removeAttribute("checkedProductNos");
        ProductOrderDto.Read order;
        if (checkedProductNos != null) {
            order = service.orderReady(myUserDetails.getMemberNo(), checkedProductNos);
        } else {
            return new ModelAndView("redirect:/mall/main");
        }
        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());
        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", order.getOrderItems());
        map.put("orderTotalPrice", order.getOrderTotalPrice());
        map.put("addresses", addresses);
        session.setAttribute("map",map);
        return new ModelAndView("/mall/order/ready").addObject("map", map);
    }

    @PostMapping("/order/check")
    public String placeOrder(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Integer addressNo, RedirectAttributes ra) {
        Map<String, Object> map = (Map<String, Object>)session.getAttribute("map");
        List<ProductOrderDto.OrderList> orderItems = (List<ProductOrderDto.OrderList>)map.get("orderItems");
        Integer orderTotalPrice = (Integer)map.get("orderTotalPrice");
        Integer productOrderNo = service.saveOrder(orderItems, orderTotalPrice, myUserDetails.getMemberNo(), addressNo);

        ra.addFlashAttribute("orderNo", productOrderNo);
//        return "redirect:/mall/order/success";
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
