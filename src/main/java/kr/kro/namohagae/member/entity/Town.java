package kr.kro.namohagae.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Town {
    private Integer townNo;
    private String townGu;
    private String townDong;
    private Double townLatitude;
    private Double  townLongitude;
}
