package kr.kro.namohagae.global.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TownDao {

    @Select("select town_no from town where town_dong=#{townDong}")
    public Integer findNoByDong(String townDong);
}
