package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardTownDao {

    public void boardTownInsertData(Board board);

    public List<Board> boardTownList(Integer townNo);
}
