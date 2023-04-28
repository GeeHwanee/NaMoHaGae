package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.BoardComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentDao {


    public Integer commentData(BoardComment boardComment);

    public List<BoardComment> commentList(Integer boardNo);
}
