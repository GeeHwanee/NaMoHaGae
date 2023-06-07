package kr.kro.namohagae.board.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardInsightDao {

    public Integer save(Integer boardNo, Integer MemberNo);

    public Integer existsByBoardNoAndMemberNo(Integer boardNo, Integer MemberNo);

    public Integer findBoardLikeEnabledByBoardNoAndMemberNo(Integer boardNo, Integer MemberNo);

}
