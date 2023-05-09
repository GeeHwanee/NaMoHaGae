package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.BoardNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardNoticeDao {


    public void save(BoardNotice boardNotice);

    public List<BoardNotice> findAll();
}
