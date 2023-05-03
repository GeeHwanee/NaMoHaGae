package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.FavoriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteDao {
    // 찜 등록
    public Integer save(Integer productNo, Integer memberNo);

    // 해당 멤버의 찜리스트
    public List<FavoriteDto.list> findListByMemberNo(Integer startRowNum, Integer endRowNum, Integer memberNo);

    // 찜 삭제
    public Integer delete(Integer productNo, Integer memberNo);

    // 찜 등록 여부 체크
    public boolean existsByMyFavorite(Integer productNo, Integer memberNo);
}
