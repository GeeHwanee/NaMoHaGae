package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.FollowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDao {

    public Integer save(Integer memberNo,Integer myMemberNo);

    public Integer delete(Integer memberNo,Integer myMemberNo);

    public List<FollowDto.FollowList> findFollowList(Integer startRownum, Integer endRownum, Integer memberNo);

    public Boolean existsByMemberNoAndFollowMemberNo(Integer memberNo,Integer myMemberNo);

    public Integer count(Integer memberNo);
}
