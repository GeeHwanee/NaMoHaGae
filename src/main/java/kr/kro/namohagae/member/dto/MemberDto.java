package kr.kro.namohagae.member.dto;

import kr.kro.namohagae.member.entity.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemberDto {

    @Data
    public static class Join{
        private Integer townNo;
        private String memberEmail;
        private String memberPassword;
        private String memberNickname;
        private String memberPhone;
        private String memberIntroduce;
        private MultipartFile memberProfileImage;


        public Member toEntity(String encodedPassword, String memberProfileImage, String memberIntroduce) {

            return Member.builder().townNo(townNo).memberEmail(memberEmail).memberPassword(encodedPassword).memberNickname(memberNickname).memberPhone(memberPhone).memberIntroduce(memberIntroduce).memberProfileImage(memberProfileImage).build();
        }

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

