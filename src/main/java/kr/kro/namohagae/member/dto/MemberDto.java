package kr.kro.namohagae.member.dto;

import kr.kro.namohagae.global.util.validation.*;
import kr.kro.namohagae.member.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class MemberDto {

    @Data
    @Builder
    public static class Join{
        private String townGu;
        private String townDong;
        @Email
        private String memberEmail;
        @Password
        private String memberPassword;
        @Nickname
        private String memberNickname;
        @Phone
        private String memberPhone;
        private String memberIntroduce;
        private MultipartFile memberProfileImage;
        private Double memberLatitude;
        private Double memberLongitude;
        private Boolean memberCheckKaKao;


        public Member toEntity(String encodedPassword, String memberProfileImage, String memberIntroduce,Integer townNo,Boolean memberCheckKaKao) {

            return Member.builder().townNo(townNo).memberEmail(memberEmail).memberPassword(encodedPassword).memberNickname(memberNickname).memberPhone(memberPhone).
                    memberIntroduce(memberIntroduce).memberProfileImage(memberProfileImage).memberLatitude(memberLatitude).memberLongitude(memberLongitude).memberCheckKaKao(memberCheckKaKao).build();
        }

    }

    @Data
    public static class UpdateMember{
        private MultipartFile profile;
        private String password;
        private String nickname;
        private String introduce;
        private String phone;
        private String townDong;
        private String latitude;
        private String longitude;

        public Member toEntity(String encodedPassword,String nickname,String introduce,String phone,String profile,Integer memberNo,Integer townNo,Double latitude,Double longitude) {

            return Member.builder().memberNo(memberNo).memberProfileImage(profile).memberPassword(encodedPassword).memberPhone(phone).townNo(townNo).memberNickname(nickname).memberLatitude(latitude).memberLongitude(longitude).memberIntroduce(introduce).build();
        }
    }
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Read{
        private Integer memberNo;
        private String memberProfile;
        private String memberNickname;
        private Integer memberGrade;
        private Integer memberPoint;
        private String memberEmail;
        private String memberIntroduce;
        private Boolean memberCheckKaKao;
        private Boolean memberDogSignEnabled;
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

    @Data
    @ToString
    @AllArgsConstructor
    public static class Preview{
        private Integer memberNo;
        private String townGu;
        private String townDong;
        private String memberNickname;
        private String memberEmail;
        private String memberPhone;
        private Boolean memberEnabled;
    }
}

