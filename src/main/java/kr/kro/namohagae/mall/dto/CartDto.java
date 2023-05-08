package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.CartDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
    @Data
    @AllArgsConstructor
    public static class Read {
        private List<CartDetail> cartDetails;
        private Integer cartTotalPrice;
    }

    @Data
    public static class Delete {
        private List<Integer> list;
    }
}
