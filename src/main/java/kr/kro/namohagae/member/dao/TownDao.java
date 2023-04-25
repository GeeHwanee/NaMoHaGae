package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.global.dto.TownDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TownDao {

    @Select("select town_no, town_dong from TOWN where town_gu=#{gu}")
    public List<Map> findByGu(String gu);

}
