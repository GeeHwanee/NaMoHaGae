package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Puchingdao {


    @Select("select t.town_no as townNo,t.town_dong as townDong,t.town_latitude as townLatitude," +
            "t.town_longitude as townLongitude,count(m.town_no) as townCnt " +
            "from TOWN t,MEMBER m   where t.TOWN_NO=m.TOWN_NO(+) " +
            "group by t.town_no,t.town_dong,t.town_latitude,t.town_longitude,m.town_no order by townNO")
    public List<PuchingDto.readTown> findAllTown();
    public List<PuchingDto.readUser> findByUsers(Double latitude,Double longitude,Integer startrownum,Integer endrownum);

}
