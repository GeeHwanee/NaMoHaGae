package kr.kro.namohagae.mall.vo;

import lombok.Data;

// 결제준비 VO

@Data
public class KakaoPayReadyVO {
    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id;
}