package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardTownListDto;
import kr.kro.namohagae.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardTownDao {

    public void boardTownInsertData(Board board);

    public List<BoardTownListDto> boardTownList(Integer townNo,@Param("searchName") String searchName, @Param("start") int start, @Param("limit") int limit);

    public Board boardTownRead(Integer boardNo);


    public Integer count();

    public Integer townReadCnt(Integer boardNo);

    public void townUpdateData(Board board);

    public Integer townDeleteData(Integer boardNo);
}
