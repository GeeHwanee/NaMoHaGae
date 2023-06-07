package kr.kro.namohagae.board.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardInsightDao {

    public Integer save(Integer boardNo, Integer memberNo);

    public Boolean existsByBoardNoAndMemberNo(Integer boardNo, Integer memberNo);

    public Boolean findBoardLikeEnabledByBoardNoAndMemberNo(Integer boardNo, Integer memberNo);

    public void updateBoardLikeEnabled(Integer boardNo, Integer memberNo);

}
