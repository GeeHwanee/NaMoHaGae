package kr.kro.namohagae.mall.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dao.CartDetailDao;
import kr.kro.namohagae.mall.dao.ProductDao;
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
    private final CartDetailDao cartDetailDao;
    private final ProductDao productDao;

/*
    // 장바구니 -> 주문 확인 이동
    @PostMapping("/mall/order")
    public String orderCart(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam(value = "checkedProductNos") List<Integer> checkedProductNos) {
        Integer memberNo = myUserDetails.getMemberNo();
        ProductOrderDto.Read order = service.orderReadyFromCart(memberNo, checkedProductNos);

        session.setAttribute("checkedProductNos", checkedProductNos);
        session.setAttribute("orderItems", order.getOrderItems());
        session.setAttribute("orderTotalPrice", order.getOrderTotalPrice());

        return "redirect:/mall/order/ready";
    }


    // 상품페이지 -> 주문 확인 이동
    @PostMapping("/mall/product/order")
    public String orderProduct(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                               @RequestParam(value = "productNo", required = false) Integer productNo) {
        Integer memberNo = myUserDetails.getMemberNo();
        ProductOrderDto.Read order = service.orderReadyFromProduct(memberNo, productNo);

        session.setAttribute("productNo",productNo);
        session.setAttribute("orderItems", order.getOrderItems());
        session.setAttribute("orderTotalPrice", order.getOrderTotalPrice());
        System.out.println("여기 통과는 했니");

        return "redirect:/mall/product/order/ready";
    }

    // ==============여기까지 따로버전
 */

    // 0515 합친버전 (test 필요)
    @PostMapping("/mall/order")
    public String order(HttpSession session, @RequestParam(value = "checkedProductNos", required = false) List<Integer> checkedProductNos) {
        if (checkedProductNos != null) {
            session.setAttribute("checkedProductNos", checkedProductNos);
        } else {
            return "redirect:/mall/main";
        }
        return "redirect:/mall/order/ready";
    }


/*
    // 장바구니 -> 주문 확인
    @GetMapping("/mall/order/ready")
    public ModelAndView orderCartList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<Integer> checkedProductNos = (List<Integer>) session.getAttribute("checkedProductNos");

        if (checkedProductNos == null) {
            return new ModelAndView("redirect:/mall/cart/list");
        }

        ProductOrderDto.Read order = service.orderReadyFromCart(myUserDetails.getMemberNo(), checkedProductNos);
        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());

        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", order.getOrderItems());
        map.put("orderTotalPrice", order.getOrderTotalPrice());
        map.put("addresses", addresses);

        return new ModelAndView("/mall/order/ready").addObject("map", map);
    }


    // 상품페이지 -> 주문 확인
    @GetMapping("/mall/product/order/ready")
    public ModelAndView orderProductList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer productNo = (Integer) session.getAttribute("productNo");
        if (productNo == null) {
            return new ModelAndView("redirect:/mall/main");
        }

        ProductOrderDto.Read order = service.orderReadyFromProduct(myUserDetails.getMemberNo(), productNo);
        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());

        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", order.getOrderItems());
        map.put("orderTotalPrice", order.getOrderTotalPrice());
        map.put("addresses", addresses);

        return new ModelAndView("/mall/product/order/ready").addObject("map", map);
    }
    //=======================여기까지 따로버전
 */


    //0515 합친버전 (test 필요)
    @GetMapping("/mall/order/ready")
    public ModelAndView orderList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<Integer> checkedProductNos = (List<Integer>)session.getAttribute("checkedProductNos");
        session.removeAttribute("checkedProductNos");
        ProductOrderDto.Read order;
        if (checkedProductNos != null) {
            order = service.orderReadyFromCart(myUserDetails.getMemberNo(), checkedProductNos);
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


    // 장바구니 -> 주문하기 (수정전)
    /*
    @PostMapping("/order/check")
    public String placeOrderFromCart(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Integer addressNo, RedirectAttributes ra) {
        List<Integer> checkedProductNos = (List<Integer>) session.getAttribute("checkedProductNos");

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
        Integer productOrderNo = service.placeOrderFromCart(items, myUserDetails.getMemberNo(), addressNo);

        for (ProductOrderDetail item : items) {
            item.setProductOrderNo(productOrderNo);
        }

        session.removeAttribute("checkedProductNos");
        ra.addFlashAttribute("orderNo", productOrderNo); // 주문결과때문에 추가했는데 수정예정
        return "redirect:/mall/order/success";
    }
     */


    /*
    // 장바구니 -> 주문하기 수정중1(0515)
    @PostMapping("/order/check")
    public String placeOrder(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Integer addressNo, RedirectAttributes ra) {
        List<Integer> checkedProductNos = (List<Integer>) session.getAttribute("checkedProductNos");

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
        Integer productOrderNo = service.placeOrderFromCart(items, myUserDetails.getMemberNo(), addressNo);

        session.removeAttribute("checkedProductNos");
        ra.addFlashAttribute("orderNo", productOrderNo);
        return "redirect:/mall/order/success";
    }


    // 상품페이지 -> 주문하기
    @PostMapping("/product/order/check")
    public String placeOrderFromProduct(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                                        @RequestParam Integer addressNo) {
        Integer productNo = (Integer) session.getAttribute("productNo");
        if (productNo == null) {
            return "redirect:/mall/main";
        }

        ProductDto.Read product = productDao.findByProductNo(productNo);
        if (product == null) {
            return "redirect:/mall/main";
        }

        // 주문 상품 정보 생성
        ProductOrderDetail item = new ProductOrderDetail(null, 2, product.getProductNo(), 1, product.getProductPrice(), true);

        // 주문 정보 저장
        Integer productOrderNo = service.placeOrderFromProduct(product.getProductNo(), myUserDetails.getMemberNo(), addressNo);

        item.setProductOrderNo(productOrderNo);

        session.removeAttribute("productNo");
        return "mall/product/order/success";
    }
    //=======================여기까지 따로버전
     */


    // 0515 합친버전 (test 필요)
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







    // 아래부터 수정해야함 (서비스에서 끌어오는 dto랑 ra에 값 담아오는거 수정)

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
