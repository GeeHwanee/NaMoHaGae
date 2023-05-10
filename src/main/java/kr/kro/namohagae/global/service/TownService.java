package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.dto.TownDto;
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


}
