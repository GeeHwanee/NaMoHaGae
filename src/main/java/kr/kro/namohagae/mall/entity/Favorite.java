package kr.kro.namohagae.mall.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Favorite {
    private Integer favoriteNo;
    private Integer memberNo;
    private Integer productNo;
}
