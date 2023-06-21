package kr.kro.namohagae.board.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardInsightDao {

    public Integer save(Integer boardNo, Integer memberNo);

    public Boolean existsByBoardNoAndMemberNo(Integer boardNo, Integer memberNo);

    public Boolean findBoardRecommendEnabledByBoardNoAndMemberNo(Integer boardNo, Integer memberNo);

    public void updateBoardRecommendEnabled(Integer boardNo, Integer memberNo);

    public Integer countByBoardNo(Integer boardNo);
}
