package kr.kro.namohagae.member.entity;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@Builder
@Alias("member")
public class Member {

    private Integer memberNo;
    private Integer townNo;
    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private String memberPhone;
    private Double memberLatitude;
    private Double memberLongitude;
    private Integer memberGrade;
    private String memberIntroduce;
    private Integer memberPoint;
    private String memberProfileImage;
    private Boolean memberDogSignEnabled;
    private Boolean memberEnabled;
    private Integer memberLoginCount;
    private Integer memberQuestionSelectCount;
    private Boolean memberLocationEnabled;


    public MemberDto.Read toReadDto() {

       return new MemberDto.Read(memberNo,ImageConstants.IMAGE_PROFILE_URL +memberProfileImage,memberNickname,memberGrade,memberPoint,memberEmail,memberIntroduce);
    }

}
