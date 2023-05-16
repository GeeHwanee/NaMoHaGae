package kr.kro.namohagae.board.service;


import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dao.CommentDao;
import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.CommentDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardComment;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    @Autowired
    private BoardDao boardDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    private NotificationService notificationService;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public Integer commentData(BoardComment boardComment,String userEmail) {

        boardComment.setMemberNo(memberDao.findNoByUsername(userEmail));
        Board boardOrigin = boardDao.findByBoardNo(boardComment.getBoardNo());
        String link;
        if(boardOrigin.getTownNo()!=0){
            link = NotificationConstants.BOARD_TOWN_LINK;
        }else{
            link = NotificationConstants.BOARD_FREE_LINK;
        }

        notificationService.save(memberDao.findByMember(boardOrigin.getMemberNo()).get(), NotificationConstants.COMMENT_CONTENT,link+boardComment.getBoardNo());
         return commentDao.commentData(boardComment);
    };

    public List<CommentDto.CommentList> commentList(Integer boardNo) {

        return commentDao.commentList(boardNo);
    }

    public void commentUpdate(BoardComment boardComment) {

        commentDao.commentUpdate(boardComment);
    }

    public void commentDelete(Integer commentNo) {

        commentDao.commentDelete(commentNo);
    }

    public CommentDto.PaginationMyCommentList myCommentList(Integer pageno,Integer memberNo) {
        Integer startRowNum = (pageno-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<CommentDto.MyCommentList> myCommentList =  commentDao.myCommentList(startRowNum,endRowNum,memberNo);
        Integer countOfFavorite = boardDao.countByMemberNo(memberNo);
        Integer countOfPage = (countOfFavorite-1)/PAGESIZE + 1;
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new CommentDto.PaginationMyCommentList(pageno, prev, start, end, next, myCommentList);
    }

}

