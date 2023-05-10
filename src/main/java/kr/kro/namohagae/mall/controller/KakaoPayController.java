package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.service.CartService;
import kr.kro.namohagae.mall.service.KakaoPayService;
import kr.kro.namohagae.mall.vo.KakaoPayApprovalVO;
import kr.kro.namohagae.mall.vo.KakaoPayReadyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;
 
@Controller
public class KakaoPayController {
    @Autowired
    private KakaoPayService kakaoPayService;
    @Autowired
    private CartService cartService;
    
    @GetMapping("/pay")
    public String kakaoPayReady(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        Integer memberNo=myUserDetails.getMemberNo();
        model.addAttribute("map", cartService.list(memberNo));
        return "mall/pay/ready";
    }
    
    // 결제 버튼 클릭 시 카카오페이 결제창 호출
    // partner_order_id : uuid에 담아 -> 테스트 결제 거치면 값을 저장안하니까
    // partner_order_id, tid : session에 담아 (null 값이니까 session에 담아서 가져와야해)
    @GetMapping("/pay/start")
	public @ResponseBody KakaoPayReadyVO kakaoPay(@RequestParam Map<String, Object> params, HttpSession session) {
    	String uuid = UUID.randomUUID().toString();
		KakaoPayReadyVO res = kakaoPayService.kakaoPay(params, uuid);
		session.setAttribute("partner_order_id", uuid);
		session.setAttribute("tid", res.getTid());
		return res;
	}
    
    // 결제 성공
    // 성공하면 partner_order_id, tid : session에서 날려
    @GetMapping("/pay/success")
    public String Success(@RequestParam("pg_token") String pgToken, HttpSession session) {
    	KakaoPayApprovalVO res = kakaoPayService.kakaoPayApprove(pgToken,session);
    	session.removeAttribute("partner_order_id");
    	session.removeAttribute("tid");
    	return "mall/pay/success";
    }
    
    @GetMapping("/pay/cancel")
    public String cancel() {
    	return "mall/pay/cancel";
    }
    
    @GetMapping("/pay/fail")
    public String fail() {
    	return "mall/pay/fail";
    }
}