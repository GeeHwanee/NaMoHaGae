package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.BoardNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardNoticeDao {


    public void save(BoardNotice boardNotice);

    public List<BoardNotice> findAll();

    public BoardNotice read(Integer boardNoticeNo);

    public Integer delete(Integer boardNoticeNo);

    public Integer increaseReadCnt(Integer boardNoticeNo);

    public void update(BoardNotice boardNotice);
}
