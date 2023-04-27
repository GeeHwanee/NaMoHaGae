package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

    public void boardFreeInsertData(Board board);

    public List<Board> boardFreeList();

    public Board boardFreeReadData(Integer boardNo);
}
