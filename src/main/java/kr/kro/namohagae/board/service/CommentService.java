package kr.kro.namohagae.board.service;


import kr.kro.namohagae.board.dao.CommentDao;
import kr.kro.namohagae.board.entity.BoardComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    public Integer commentData(BoardComment boardComment) {


        return commentDao.commentData(boardComment);
    };

    public List<BoardComment> commentList(Integer boardNo) {

        return commentDao.commentList(boardNo);
    }

}

