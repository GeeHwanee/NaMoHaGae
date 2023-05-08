package kr.kro.namohagae.board.service;


import kr.kro.namohagae.board.dao.CommentDao;

import kr.kro.namohagae.board.entity.BoardComment;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;
    @Autowired
    MemberDao memberDao;

    public Integer commentData(BoardComment boardComment,String userEmail) {

        boardComment.setMemberNo(memberDao.findNoByUsername(userEmail));
         return commentDao.commentData(boardComment);
    };

    public List<BoardComment> commentList(Integer boardNo) {
        
        return commentDao.commentList(boardNo);
    }

    public void commentUpdate(BoardComment boardComment) {

        commentDao.commentUpdate(boardComment);
    }

    public void commentDelete(Integer commentNo) {

        commentDao.commentDelete(commentNo);
    }
}

