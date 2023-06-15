package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberDao {

    public void save(Member member);
    public Boolean updateMember(@Param("no") Integer no, @Param("password") String password, @Param("nickname") String nickname,@Param("phone")String phone,
                                @Param("townNo")Integer townNo,@Param("profile")String profile,@Param("longitude")Double longitude,@Param("latitude")Double latitude,@Param("introduce")String introduce);

    public Optional<Member> findByMemberNo(Integer no);

    public Integer findNoByUsername(String email);

    public Optional<Member> findByUsername(String email);

    public Integer increaseMemberLoginCount(Integer no);

    public Integer resetMemberLoginCount(Integer no);

    public Integer memberEnabled(Integer no,Boolean enabled);

    public Integer updatePoint(Integer no,@Param("point")Integer point);

    public Integer locationEnabled(Integer no,Boolean enabled);
    public Integer dogSignEnabled(Integer no,Boolean enabled);

    public String findByEmail(String nickname,String phone);

    public Boolean existsByNickname(String nickname);

    public Boolean existsByEmail(String email);

    public Integer updateProfile(String profile,Integer no);

    //public Integer updateEnable

    public Integer disabled(Integer memberNo);

    public String findEmailByNicknameAndPhone(String nickname, String phone);

    public Boolean changePassword(String memberPassword,String memberEmail);

    public Integer findMemberPointByMemberNo(Integer memberNo);

    public Integer updateMemberPointByMemberNo(Integer memberPoint, Integer memberNo);
    public Integer findMemberNoByNickname(String nickname);

    public List<MemberDto.Preview> preview(String url, String searchName);
}
