package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.BoardNoticeDto;
import kr.kro.namohagae.board.entity.BoardNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardNoticeDao {


    public void save(BoardNotice boardNotice);

    public List<BoardNotice> findAll();

    public List<BoardNoticeDto.Preview> preview();
    public List<BoardNoticeDto.Preview> noticeList(Integer start, Integer end);

    public BoardNoticeDto.Read findByBoardNoticeNo(Integer boardNoticeNo);

    public Integer delete(Integer boardNoticeNo);

    public void update(BoardNotice boardNotice);
    public Integer count();
}
