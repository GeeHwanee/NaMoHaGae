package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

    public void save(Board board);
    public List<BoardDto.Preview> preview(Integer townNo,Integer memberNo, String searchName, String sorting, Integer start, Integer limit);
    public Integer countPreview(Integer townNo, Integer memberNo, String searchName);
    public BoardDto.Read readByBoardNo(String url, Integer boardNo);
    public Boolean findBoardEnabledByBoardNo(Integer boardNo);
    public Board findByBoardNo(Integer boardNo);
    public Integer findMemberNoByBoardNo(Integer boardNo);
    public Integer countByMemberNo(Integer memberNo);
    public void delete(Integer boardNo);
    public void update(Board board);


}
