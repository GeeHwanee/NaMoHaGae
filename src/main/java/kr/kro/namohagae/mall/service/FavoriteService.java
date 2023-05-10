package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.FavoriteDao;
import kr.kro.namohagae.mall.dto.FavoriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public FavoriteDto.Pagination list(Integer pageNo, Integer productNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<FavoriteDto.list> favorites =  favoriteDao.findListByMemberNo(startRowNum, endRowNum, productNo);
        Integer countOfFavorite = favoriteDao.count(productNo);
        Integer countOfPage = (countOfFavorite-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new FavoriteDto.Pagination(pageNo, prev, start, end, next, favorites);
    }


    public Boolean checkFavorite(Integer productNo,Integer memberNo){
        return  favoriteDao.existsByMyFavorite(productNo,memberNo);
    }

    public Map<String,Boolean> favorite(Integer productNo, Integer memberNo) {
        Boolean alreadyFavorite = favoriteDao.existsByMyFavorite(productNo,memberNo);
        Map<String,Boolean> map = new HashMap<>();
        if (alreadyFavorite==false) {
            favoriteDao.save(productNo,memberNo);
            map.put("favorite", true);
        } else {
            favoriteDao.delete(productNo,memberNo);
            map.put("favorite", false);
        }
        return map;
    }
}
