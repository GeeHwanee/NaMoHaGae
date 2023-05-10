package kr.kro.namohagae.board.dao;


import kr.kro.namohagae.board.dto.CommentListDto;
import kr.kro.namohagae.board.entity.BoardComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {


    public Integer commentData(BoardComment boardComment);

    public List<CommentListDto> commentList(Integer boardNo);

    public void commentUpdate(BoardComment boardComment);

    public void commentDelete(Integer commentNo);
}
