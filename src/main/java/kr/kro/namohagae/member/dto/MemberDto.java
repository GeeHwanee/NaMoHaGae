package kr.kro.namohagae.member.dto;

import kr.kro.namohagae.member.entity.Member;
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

    @Data
    public static class UpdateMember{
        private String memberPassword;
        private String memberNickname;
        private String memberIntroduce;
        private String memberPhone;
        private Integer townNo;

        public Member toEntity(String encodedPassword) {
            return Member.builder().memberPassword(encodedPassword).memberPhone(memberPhone).townNo(townNo).memberNickname(memberNickname).build();
        }
    }
}

