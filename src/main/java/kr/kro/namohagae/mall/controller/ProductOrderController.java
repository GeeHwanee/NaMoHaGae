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

    /*
    // 장바구니, 상품페이지 -> 주문 확인 이동 (따로 만들어서 잠시 주석)
    @PostMapping("/mall/order")
    public String orderReady(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                        @RequestParam(value = "checkedProductNos", required = false) Integer[] checkedProductNos,
                        @RequestParam(value = "productNo", required = false) Integer productNo) {
        Integer memberNo = myUserDetails.getMemberNo();
        ProductOrderDto.Read order;

        System.out.println("컨트롤러 진입성공?");
        System.out.println(productNo + "productNo 나오니");
        
        
        // 장바구니에서 주문하는 경우
        if (checkedProductNos != null) {
            order = service.orderReadyFromCart(memberNo, Arrays.asList(checkedProductNos));

        // 상품 페이지에서 주문하는 경우
        } else if (productNo != null) {
            order = service.orderReadyFromProduct(memberNo, productNo);
            System.out.println(productNo + "productNo 나오니2");
        // 선택한 상품이나 장바구니가 없는 경우
        } else {
            return "redirect:/mall/cart";
        }

        session.setAttribute("checkedProductNos", checkedProductNos);
        session.setAttribute("orderItems", order.getOrderItems());
        session.setAttribute("orderTotalPrice", order.getOrderTotalPrice());

        return "redirect:/mall/order/ready";
    }
     */


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

    /*
    // 주문 확인 (수정버전1)
    @GetMapping("/mall/order/ready")
    public ModelAndView orderDetailList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer[] checkedProductNos = (Integer[]) session.getAttribute("checkedProductNos");
        List<ProductDto.Read> orderItems = (List<ProductDto.Read>) session.getAttribute("orderItems");
        Map<String, Object> map = new HashMap<>();

        // 상품read에서 주문한 경우
        if (checkedProductNos == null) {
            map.put("orderItems", orderItems);
            map.put("orderTotalPrice", orderItems.get(0).getProductPrice()); // 첫 번째 상품 가격 사용
            List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());
            map.put("addresses", addresses);
        }
        // 장바구니에서 주문한 경우
        else if (checkedProductNos != null) {
            ProductOrderDto.Read order = service.orderReady(myUserDetails.getMemberNo(), Arrays.asList(checkedProductNos));
            map.put("orderItems", order.getOrderItems());
            map.put("orderTotalPrice", order.getOrderTotalPrice());
            List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());
            map.put("addresses", addresses);
        }

        else {
            return new ModelAndView("redirect:/mall/cart/list");
        }

        return new ModelAndView("/mall/order/ready", map);
    }
     */

    // 상품페이지 -> 주문 확인
    @GetMapping("/mall/product/order/ready")
    public ModelAndView orderProductList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer productNo = (Integer) session.getAttribute("productNo");
        System.out.println(productNo + "컨트롤러 no 보이니");
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


    /*
    // 주문확인 (수정버전2)
    @GetMapping("/mall/order/ready")
    public ModelAndView orderDetailList(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer[] checkedProductNos = (Integer[]) session.getAttribute("checkedProductNos");
        Integer productNo = (Integer) session.getAttribute("productNo");
        List<ProductDto.Read> orderItems = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        // 상품read에서 주문한 경우
        if (checkedProductNos == null) {
            // productNo로 상품 정보를 가져온 후, orderItems 리스트에 추가
            ProductDto.Read product = productDao.findByProductNo(productNo);
            orderItems.add(product);

            Integer orderTotalPrice = product.getProductPrice();
            map.put("orderItems", orderItems);
            map.put("orderTotalPrice", orderTotalPrice);
        }
        // 장바구니에서 주문한 경우
        else if (checkedProductNos != null) {
            ProductOrderDto.Read order = service.orderReadyFromCart(myUserDetails.getMemberNo(), Arrays.asList(checkedProductNos));
            map.put("orderItems", order.getOrderItems());
            map.put("orderTotalPrice", order.getOrderTotalPrice());
        } else {
            return new ModelAndView("redirect:/mall/cart/list");
        }

        List<AddressDto.Read> addresses = service.findAddress(myUserDetails.getMemberNo());
        map.put("addresses", addresses);

        return new ModelAndView("/mall/order/ready", map);
    }
     */


    // 장바구니 -> 주문하기
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
        System.out.println(addressNo+"주소 null이야..?");

        item.setProductOrderNo(productOrderNo);

        session.removeAttribute("productNo");
        return "mall/product/order/success";
    }



    /*
    // 주문하기 (수정중)
    @PostMapping("/order/check")
    public String placeOrder(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Integer addressNo) {
        List<ProductDto.Read> orderItems = (List<ProductDto.Read>) session.getAttribute("orderItems");
        Integer[] checkedProductNos = (Integer[]) session.getAttribute("checkedProductNos");
        List<ProductOrderDetail> items = new ArrayList<>();

        // 상품 read에서 주문한 경우
        if (checkedProductNos == null) {
            for (ProductDto.Read orderItem : orderItems) {
                ProductOrderDetail item = new ProductOrderDetail(null, 2, orderItem.getProductNo(), 1,
                        orderItem.getProductPrice(), true);
                items.add(item);
            }
        }
        // 장바구니에서 주문한 경우
        else {
            List<CartDetail> cartDetails = cartDetailDao.findByMemberNoAndProductNos(myUserDetails.getMemberNo(), checkedProductNos);
            for (CartDetail cartDetail : cartDetails) {
                ProductDto.Read product = productDao.findByProductNo(cartDetail.getProductNo());
                ProductOrderDetail item = new ProductOrderDetail(null, 2, cartDetail.getProductNo(), cartDetail.getCartDetailCount(),
                        product.getProductPrice(), true);
                items.add(item);
            }
        }

        // 주문 정보 저장
        if (checkedProductNos == null) {
            Integer productOrderNo = service.placeOrderFromProduct(myUserDetails.getMemberNo(), orderItems.get(0).getProductNo(), addressNo);
            for (ProductOrderDetail item : items) {
                item.setProductOrderNo(productOrderNo);
            }
        } else {
            Integer productOrderNo = service.placeOrderFromCart(items, myUserDetails.getMemberNo(), addressNo);
            for (ProductOrderDetail item : items) {
                item.setProductOrderNo(productOrderNo);
            }
        }

        session.removeAttribute("checkedProductNos");
        session.removeAttribute("orderItems");
        return "/mall/order/success";
    }
     */






    
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
