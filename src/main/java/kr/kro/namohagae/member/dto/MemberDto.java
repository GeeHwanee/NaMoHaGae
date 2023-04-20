package kr.kro.namohagae.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemberDto {

    @Data
    public static class Join{
        private Integer memberNo;
        private Integer townNo;
        private String memberEmail;
        private String memberPassword;
        private String memberNickname;
        private String memberPhone;
        private String memberIntroduce;

    }
}
