package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Puchingdao {


    @Select("select town_no as townNo,town_dong as townDong,town_latitude as townLatitude,town_longitude as townLongitude from TOWN")
    public List<PuchingDto.readTown> findAllTown();
}
