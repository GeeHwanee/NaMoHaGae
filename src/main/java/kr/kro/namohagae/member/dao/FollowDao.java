package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.dto.FollowDto;
import kr.kro.namohagae.member.entity.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDao {

    public Integer save(Integer memberNo,Integer followMemberNo);

    public Integer delete(Integer followNo);

    public List<FollowDto.list> findFollowList(Integer startRownum, Integer endRownum,Integer memberNo);
}
