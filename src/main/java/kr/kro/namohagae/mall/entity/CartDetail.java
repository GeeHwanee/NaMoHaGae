package kr.kro.namohagae.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CartDetail {
    private Integer cartDetailNo;
    private Integer memberNo;
    private Integer cartNo;
    private Integer productNo;
    private Integer cartDetailCount;
    private Integer cartDetailPrice;

    public void setCartDetailCount(int i) {
    }
    public void setCartDetailPrice(int i) {
    }


}
