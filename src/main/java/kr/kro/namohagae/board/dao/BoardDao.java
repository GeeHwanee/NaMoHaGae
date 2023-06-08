package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.BoardMainList;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {

    public void save(Board board);
    public List<BoardDto.Preview> preview(Integer townNo, String searchName, String sorting, Integer start, Integer end);
    public BoardDto.Read readByBoardNo(Integer boardNo);




    public BoardList boardFreeReadData(Integer boardNo);

    public Integer boardDeleteData(Integer boardNo);

    public void boardUpdateData(Board board);

    List<BoardList> pagingList(@Param("searchName") String searchName,@Param("start") int start, @Param("limit") int limit);

    List<BoardList> readCountList(@Param("searchName") String searchName,@Param("start") int start, @Param("limit") int limit);

    List<BoardList> recommendCountList(@Param("searchName") String searchName,@Param("start") int start, @Param("limit") int limit);

    List<BoardMainList> mainReadList();

    List<BoardMainList> mainRecommendList();
    public int boardCount();



    public Integer isLikeExists(Integer boardNo, Integer memberNo);
    public void insertLike(Integer boardNo,Integer memberNo);
    public void removeLike(Integer boardNo, Integer memberNo);

    public void updateLike(Integer boardNo,Integer memberNo);

    public Integer findLike(Integer boardNo, Integer memberNo);
    public void readCnt(Integer boardNo);

    public void goodLike(Integer boardNo);

    public void badLike(Integer boardNo);

    public List<BoardDto.FindAllByMemberNo> findAllByMemberNo(Integer startRownum,Integer endRownum,Integer memberNo);
    public Integer countByMemberNo(Integer memberNo);

    public Board findByBoardNo(Integer boardNo);

    public Integer findMemberNoByBoardNo(Integer boardNo);

    Integer count();

    Integer countPreview(Integer townNo, String searchName);
}
