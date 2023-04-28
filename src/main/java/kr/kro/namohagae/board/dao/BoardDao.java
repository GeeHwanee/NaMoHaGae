package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {

    public void boardFreeInsertData(Board board);

    public List<Board> boardFreeList();

    public Board boardFreeReadData(Integer boardNo);

    public Board boardDeleteData(Integer boardNo);

    public void boardUpdateData(Board board);

    List<Board> pagingList(@Param("start") int start, @Param("limit") int limit);
    public int boardCount();
}
