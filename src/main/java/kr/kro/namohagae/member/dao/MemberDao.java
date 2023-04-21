package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberDao {

    public void save(Member member);

    public Integer updateMember(Integer no,String password,String nickname,String phone,String townNo);

    public Optional<Member> findByMemberNo(Integer no);


}
