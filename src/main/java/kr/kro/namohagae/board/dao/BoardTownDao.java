package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardMainList;
import kr.kro.namohagae.board.dto.BoardTownListDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardList;
import kr.kro.namohagae.global.entity.Town;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardTownDao {

    public void boardTownInsertData(Board board);

    public List<Town> townList();
    public List<BoardTownListDto> boardTownList(Integer townNo,@Param("searchName") String searchName, @Param("start") int start, @Param("limit") int limit);

    public List<BoardTownListDto> boardTownReadCountList(Integer townNo,@Param("searchName") String searchName,@Param("start") int start, @Param("limit") int limit);

    public List<BoardTownListDto> boardTownRecommendCountList(Integer townNo,@Param("searchName") String searchName,@Param("start") int start, @Param("limit") int limit);
    List<BoardMainList> mainReadList();

    List<BoardMainList> mainRecommendList();
    public BoardList boardTownRead(Integer boardNo);


    public Integer count();

    public Integer townReadCnt(Integer boardNo);

    public void townUpdateData(Board board);

    public Integer townDeleteData(Integer boardNo);
}
