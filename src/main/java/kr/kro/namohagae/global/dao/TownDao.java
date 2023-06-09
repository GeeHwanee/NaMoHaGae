package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.entity.Town;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TownDao {

    public Town findByMemberNo(Integer memberNo);
    public Integer findNoByDong(String townDong);
    public String findTownDongByTownNo(Integer townNo);
    public List<TownDto.Dong> findDong();
    public List<TownDto.Read> findTownDongByGu(String townGu);
    public TownDto.userGuAndDong findFuck(Integer memberNo);
    public Boolean save(Town town);
    public Boolean delete(Integer townNo);
    public Boolean checkDong(String townDong);
    public List<TownDto.FindAll> findAll(Integer startRownum,Integer endRownum);
    public List<TownDto.FindAll> findAllByGu(String gu,Integer startRownum,Integer endRownum);
    public Integer countAll();
    public Integer countAllByGu(String gu);

    public List<TownDto.Gu> findGu();

}
