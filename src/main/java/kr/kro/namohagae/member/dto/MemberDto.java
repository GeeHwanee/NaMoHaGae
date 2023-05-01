package kr.kro.namohagae.member.dto;

import kr.kro.namohagae.member.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemberDto {

    @Data
    public static class Join{
        private String townDong;
        private String memberEmail;
        private String memberPassword;
        private String memberNickname;
        private String memberPhone;
        private String memberIntroduce;
        private MultipartFile memberProfileImage;
        private Double memberLatitude;
        private Double memberLongitude;


        public Member toEntity(String encodedPassword, String memberProfileImage, String memberIntroduce,Integer townNo) {

            return Member.builder().townNo(townNo).memberEmail(memberEmail).memberPassword(encodedPassword).memberNickname(memberNickname).memberPhone(memberPhone).
                    memberIntroduce(memberIntroduce).memberProfileImage(memberProfileImage).memberLatitude(memberLatitude).memberLongitude(memberLongitude).build();
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
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Read{
        private String memberProfile;
        private String memberNickname;
        private Integer memberGrade;
        private Integer memberPoint;
        private String memberEmail;
        private String memberIntroduce;
    }
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Profile{
        private String memberProfile;
        private String memberNickname;
        private Integer memberGrade;
        private String memberIntroduce;
    }
}

