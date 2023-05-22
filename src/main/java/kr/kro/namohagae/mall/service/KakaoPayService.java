package kr.kro.namohagae.mall.service;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.mall.vo.KakaoPayApprovalVO;
import kr.kro.namohagae.mall.vo.KakaoPayReadyVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoPayService {

	private String adminKey = "5f3b4ffd0b0c2f27e17daedae91018af";

	// 결제요청
    public KakaoPayReadyVO kakaoPay(Map<String, Object> params, String uuid) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "KakaoAK " + adminKey);

			MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();

			payParams.add("cid", "TC0ONETIME"); // 테스트코드
			payParams.add("partner_order_id", uuid);
			payParams.add("partner_user_id", "namohagae");
			payParams.add("item_name", params.get("item_name"));
			payParams.add("quantity", params.get("quantity"));
			payParams.add("total_amount", params.get("total_amount"));
			payParams.add("tax_free_amount", params.get("tax_free_amount"));
			payParams.add("approval_url", "http://localhost:8081/mall/order/success");
			payParams.add("cancel_url", "http://localhost:8081/pay/cancel");
			payParams.add("fail_url", "http://localhost:8081/pay/fail");

			// 카카오페이 결제준비 api 요청
			HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);

			RestTemplate template = new RestTemplate();
			String url = "https://kapi.kakao.com/v1/payment/ready";

			// 요청결과
			KakaoPayReadyVO res = template.postForObject(url, request, KakaoPayReadyVO.class);

			return res;
    }
    
    
    // 결제승인
    public KakaoPayApprovalVO kakaoPayApprove(String pgToken, HttpSession session) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "KakaoAK " + adminKey);
			headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

			MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();

			payParams.add("cid", "TC0ONETIME");
			payParams.add("tid", session.getAttribute("tid"));
			payParams.add("partner_order_id", session.getAttribute("partner_order_id"));
			payParams.add("partner_user_id", "namohagae");
			payParams.add("pg_token", pgToken);

			// 카카오페이 결제요청 api 요청
        HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);
        
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";
        
     // 요청결과
        KakaoPayApprovalVO res = template.postForObject(url, request, KakaoPayApprovalVO.class);
        
        return res;
    }
}