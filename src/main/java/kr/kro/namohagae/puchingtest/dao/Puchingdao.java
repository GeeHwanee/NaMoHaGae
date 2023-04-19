package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Puchingdao {


    @Select("select town_no,town_dong,town_latitude,town_longitude from TOWN")
    public List<PuchingDto.read> findAllTown();
}
