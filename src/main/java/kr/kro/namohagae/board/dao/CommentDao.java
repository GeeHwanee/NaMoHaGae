package kr.kro.namohagae.board.dao;


import kr.kro.namohagae.board.dto.CommentDto;
import kr.kro.namohagae.board.entity.BoardComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {


    public Integer commentData(BoardComment boardComment);

    public List<CommentDto.CommentList> commentList(Integer boardNo);

    public void commentUpdate(BoardComment boardComment);

    public void commentDelete(Integer commentNo);

    public List<CommentDto.MyCommentList> myCommentList(Integer startRowNum,Integer endRowNum,Integer memberNo);
}
