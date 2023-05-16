package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.Address;
import lombok.Data;

public class AddressDto {
    @Data
    public static  class save{
        private String addressName;
        private String addressPostcode;
        private String addressAddress;
        private String addressAddressDetail;

        public Address toEntity(Integer memberNo,String name){
            return Address.builder().addressAddress(addressAddress).addressName(name).addressAddressDetail(addressAddressDetail).addressPostcode(addressPostcode).memberNo(memberNo).defaultAddressEnabled(false).build();
        }
    }


    @Data
    public static class Read{
        private Integer addressNo;
        private String addressName;
        private String addressPostcode;
        private String addressAddress;
        private String addressAddressDetail;
        private boolean defaultAddressEnabled;
    }
}
