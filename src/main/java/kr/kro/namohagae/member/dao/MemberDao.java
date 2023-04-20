package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    public void save(Member member);

}
