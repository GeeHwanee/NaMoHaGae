package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.BoardComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {


    public Integer commentData(BoardComment boardComment);

    public List<BoardComment> commentList(Integer boardNo);

    public void commentUpdate(BoardComment boardComment);

    public BoardComment commentDelete(Integer commentNo);
}
