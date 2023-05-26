package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.entity.Town;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {
    private final TownDao townDao;

    public List<TownDto.Read> findTownDongByGu(String townGu){
        return townDao.findTownDongByGu(townGu);
    }

    public List<TownDto.Gu> findGu(){return  townDao.findGu();}

    public TownDto.userGuAndDong findFuck(Integer memberNo){

        return townDao.findFuck(memberNo);
    }
    public Boolean checkDong(String townDong){
        return townDao.checkDong(townDong);
    }
    public Boolean delete(Integer townNo){
        return  townDao.delete(townNo);
    }
    public Boolean save(TownDto.save dto){
        String strLongitude= dto.getTownLongitude().substring(0,11);
        String strLatitude= dto.getTownLatitude().substring(0,11);
        Double longitude = Double.parseDouble(strLongitude);
        Double latitude = Double.parseDouble(strLatitude);
       Town town = dto.toEntity(latitude,longitude);
        return townDao.save(town);
    }
}
