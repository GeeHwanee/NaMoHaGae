package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.BoardLikeDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {



    public void boardFreeInsertData(Board board);
    public List<Board> boardFreeList();

    public Board boardFreeReadData(Integer boardNo);

    public Integer boardDeleteData(Integer boardNo);

    public void boardUpdateData(Board board);

    List<BoardList> pagingList(@Param("start") int start, @Param("limit") int limit);
    public int boardCount();

    public Integer increaseReadCnt(Integer boardNo);

    public Integer isLikeExists(Integer boardNo, Integer memberNo);
    public void insertLike(Integer boardNo,Integer memberNo);
    public void removeLike(Integer boardNo, Integer memberNo);

    public void goodLike(Integer boardNo);

    public void badLike(Integer boardNo);

    public List<BoardDto.FindAllByMemberNo> findAllByMemberNo(Integer startRownum,Integer endRownum,Integer memberNo);
    public Integer countByMemberNo(Integer memberNo);
}
