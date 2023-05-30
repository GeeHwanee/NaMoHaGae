package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.dto.ReportDto;
import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.entity.Town;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {
    private final TownDao townDao;
    private final static Integer PAGESIZE=10;
    private final static Integer BLOCKSIZE=5;

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


    public TownDto.Pagination findAll(Integer pageno) {
        Integer countOfProduct = townDao.countAll();
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<TownDto.FindAll> towns = townDao.findAll(startRownum, endRownum);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new TownDto.Pagination(pageno, prev, start, end, next, towns);
    }
    public TownDto.Pagination findAllByGu(Integer pageno,String gu) {
        Integer countOfProduct = townDao.countAllByGu(gu);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<TownDto.FindAll> towns = townDao.findAllByGu(gu,startRownum, endRownum);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new TownDto.Pagination(pageno, prev, start, end, next, towns);
    }

}
