package kr.kro.namohagae.mall.vo;

import lombok.Data;

//결제생성 VO

@Data
public class KakaoPayApprovalVO {
    private String aid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
}