package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.entity.Town;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TownDao {

    public Integer findNoByDong(String townDong);
    public List<TownDto.Gu> findGu();
    public List<TownDto.Read> findTownDongByGu(String townGu);
    public TownDto.userGuAndDong findFuck(Integer memberNo);
    public Boolean save(Town town);
    public Boolean delete(Integer townNo);
    public Boolean checkDong(String townDong);
}
