package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.BoardNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardNoticeDao {


    public void save(BoardNotice boardNotice);
}
