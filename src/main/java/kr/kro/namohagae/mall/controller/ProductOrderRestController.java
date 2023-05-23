package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.mall.service.KakaoPayService;
import kr.kro.namohagae.mall.vo.KakaoPayReadyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductOrderRestController {
    private final KakaoPayService kakaoPayService;

    @GetMapping("/pay/start")
    public ResponseEntity<KakaoPayReadyVO> kakaoPay(@RequestParam Map<String, Object> params, HttpSession session) {
        String uuid = UUID.randomUUID().toString();
        KakaoPayReadyVO res = kakaoPayService.kakaoPay(params, uuid);
        session.setAttribute("partner_order_id", uuid);
        session.setAttribute("tid", res.getTid());
        return ResponseEntity.ok(res);
    }
}
