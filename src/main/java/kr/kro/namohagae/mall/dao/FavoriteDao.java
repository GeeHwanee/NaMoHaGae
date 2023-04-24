package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteDao {
    // 찜리스트 저장
    public Integer save(Favorite favorite);

    // 해당 멤버의 찜리스트 찾기
    public List<Favorite> findByMemberNo(Integer memberNo);

    // 찜리스트 삭제
    public Integer delete(List<Integer> list, Integer memberNo);
}
