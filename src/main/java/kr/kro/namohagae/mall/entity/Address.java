package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Address {
    private Integer addressNo;
    private Integer memberNo;
    private String addressName;
    private String addressPostcode;
    private String addressAddress;
    private String addressAddressDetail;
}
