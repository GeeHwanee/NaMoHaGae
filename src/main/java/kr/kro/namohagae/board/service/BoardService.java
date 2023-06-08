package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dao.BoardNoticeDao;
import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.BoardMainList;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardList;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {


    private final BoardDao boardDao;
    private final BoardTownDao boardTownDao;
    private final BoardNoticeDao boardNoticeDao;
    private final NotificationService notificationService;
    private final MemberDao memberDao;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public void boardFreeInsertData(BoardDto.write boardDto, String userEmail) {


        Board board = boardDto.toEntity(memberDao.findNoByUsername(userEmail), boardDto.getTitle(), boardDto.getContent());


        boardDao.boardFreeInsertData(board);
    }



    public BoardList boardFreeReadData(Integer boardNo) {

        return boardDao.boardFreeReadData(boardNo);
    }

    public Integer boardDeleteData(Integer boardNo) {


        return boardDao.boardDeleteData(boardNo);
    }

    public void boardUpdateData(Board board) {

        boardDao.boardUpdateData(board);
    }

    int pageLimit = 10; // 한 페이지당 보여줄 글 갯수
    int blockLimit = 5; // 하단에 보여줄 페이지 번호 갯수

    public List<BoardList> pagingList(String searchName,int page) {

        /*
        1페이지당 보여지는 글 갯수 5
            1page => 0
            2page => 5
            3page => 10
         */

        int pagingStart = (page - 1) * pageLimit;

        return boardDao.pagingList(searchName,pagingStart, pageLimit);
    }

    public List<BoardList> readCountList(String searchName,int page) {


        int pagingStart = (page - 1) * pageLimit;

        return boardDao.readCountList(searchName,pagingStart, pageLimit);
    }

    public List<BoardList> recommendCountList(String searchName,int page) {

        int pagingStart = (page - 1) * pageLimit;

        return boardDao.recommendCountList(searchName,pagingStart,pageLimit);
    }

    public PageDto pagingParam(int page,Integer townNo) {
        // 전체 글 갯수 조회
        int boardCount = 0;
        if(townNo != 0) {
            boardCount = boardTownDao.boardTownCount(townNo);

        } else {
            boardCount = boardDao.boardCount();
        }

        // 전체 페이지 갯수 계산(10/3=3.33333 => 4)
        int maxPage = (int) (Math.ceil((double) boardCount / pageLimit));
        // 시작 페이지 값 계산(1, 4, 7, 10, ~~~~)
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        // 끝 페이지 값 계산(3, 6, 9, 12, ~~~~)
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDto pageDTO = new PageDto();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }

    public List<BoardMainList> mainReadList(){

        return boardDao.mainReadList();
    }

    public List<BoardMainList> mainRecommendList(){

        return boardDao.mainRecommendList();
    }



    public Boolean isLikeExists(Integer boardNo, Integer memberNo) {

        Integer count = boardDao.isLikeExists(boardNo,memberNo);
        System.out.println("갯수 : " + count);
        return count != null && count.intValue() > 0;

    }

    public void insertLike(Integer boardNo, Integer memberNo) {
        System.out.println("갯수 : " + memberNo+boardNo);
        Board board = boardDao.findByBoardNo(boardNo);
        Member member = memberDao.findByMemberNo(board.getMemberNo()).get();
        notificationService.save(member, NotificationConstants.RECOMMEND_CONTENT, NotificationConstants.BOARD_FREE_LINK+boardNo);
        boardDao.insertLike(boardNo,  memberNo);
    }
    public void readCnt(Integer boardNo) {

        boardDao.readCnt(boardNo);
    }

    public void updateLike(Integer boardNo, Integer memberNo) {

        boardDao.updateLike(boardNo,memberNo);
    }

    public Integer findLike(Integer boardNo, Integer memberNo) {

        return boardDao.findLike(boardNo, memberNo);
    }
    public void removeLike(Integer memberNo, Integer boardNo) {
        System.out.println("갯수 : " + memberNo+boardNo);
        boardDao.removeLike(memberNo, boardNo);
    }
    public void goodLike(Integer boardNo) {

        boardDao.goodLike(boardNo);
        Board board = boardDao.findByBoardNo(boardNo);
        Member member = memberDao.findByMemberNo(board.getMemberNo()).get();
        String link;
        if(board.getTownNo()!=0){
            link = NotificationConstants.BOARD_TOWN_LINK;
        }else{
            link = NotificationConstants.BOARD_FREE_LINK;
        }
        notificationService.save(member, NotificationConstants.RECOMMEND_CONTENT,link+board.getBoardNo());
    }
    public void badLike(Integer boardNo) {

        boardDao.badLike(boardNo);
    }
    public BoardDto.Pagination memberList(Integer pageno, Integer memberNo) {
        Integer startRowNum = (pageno-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<BoardDto.FindAllByMemberNo> board =  boardDao.findAllByMemberNo(startRowNum,endRowNum, memberNo);
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
        return new BoardDto.Pagination(pageno, prev, start, end, next, board);
    }
}

