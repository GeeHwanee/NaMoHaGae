package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.CartDetail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
    @Data
    public static class Read {
        private Integer cartNo;
        private Integer memberNo;
        private Integer cartTotalPrice;
        private List<CartDetail> cartDetails;
    }
}
