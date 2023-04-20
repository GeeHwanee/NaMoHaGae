package kr.kro.namohagae.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Follow{
    private Integer followNo;
    private Integer memberNo;
    private Integer followMemberNo;
}
