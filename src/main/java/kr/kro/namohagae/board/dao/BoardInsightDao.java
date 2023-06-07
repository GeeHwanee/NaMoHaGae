package kr.kro.namohagae.board.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardInsightDao {

    public Integer save(Integer boardNo, Integer MemberNo);

    public Boolean existsByBoardNoAndMemberNo(Integer boardNo, Integer MemberNo);

    public Boolean findBoardLikeEnabledByBoardNoAndMemberNo(Integer boardNo, Integer MemberNo);

    public Integer updateBoardLikeEnabled(Boolean boardLikeEnabled, Integer boardNo, Integer MemberNo);

}
