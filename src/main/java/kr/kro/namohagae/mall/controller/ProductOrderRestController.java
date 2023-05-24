package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductOrderDto;
import kr.kro.namohagae.mall.service.KakaoPayService;
import kr.kro.namohagae.mall.service.ProductOrderService;
import kr.kro.namohagae.mall.vo.KakaoPayReadyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductOrderRestController {
    private final KakaoPayService kakaoPayService;
    private final ProductOrderService service;

    @GetMapping("/pay/start")
    public ResponseEntity<KakaoPayReadyVO> kakaoPay(@RequestParam Map<String, Object> params, HttpSession session) {
        String uuid = UUID.randomUUID().toString();
        KakaoPayReadyVO res = kakaoPayService.kakaoPay(params, uuid);
        session.setAttribute("partner_order_id", uuid);
        session.setAttribute("tid", res.getTid());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/order/check")
    public ResponseEntity<Integer> placeOrder(HttpSession session, @AuthenticationPrincipal MyUserDetails myUserDetails,
                                              @RequestParam Integer addressNo, @RequestParam(required = false) Integer usedMemberPoint,
                                              @RequestParam String orderTotalPrice) {
        Map<String, Object> map = (Map<String, Object>) session.getAttribute("map");
        List<ProductOrderDto.OrderList> orderItems = (List<ProductOrderDto.OrderList>) map.get("orderItems");
        Integer totalPrice = Integer.parseInt(orderTotalPrice.replace("원", "").trim());
        Integer productOrderNo = service.saveOrder(orderItems, totalPrice, myUserDetails.getMemberNo(), addressNo, usedMemberPoint);
        session.setAttribute("orderNo", productOrderNo);
        return ResponseEntity.ok(productOrderNo);
    }
}
