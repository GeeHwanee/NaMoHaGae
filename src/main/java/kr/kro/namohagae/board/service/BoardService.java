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
import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dao.MemberDao;
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

    public void save(BoardDto.Write boardDto, Integer memberNo) {
        Board board = boardDto.toEntity(boardDto.getTownNo(), memberNo, boardDto.getBoardTitle(), boardDto.getBoardContent());
        boardDao.save(board);
    }

    public BoardDto.PaginationPreview preview(Integer townNo, String searchName, String sorting, Integer pageNo) {

        Integer start = (pageNo-1)*PAGESIZE + 1;
        Integer end = start + PAGESIZE - 1;
        List<BoardDto.Preview> preview =  boardDao.preview(townNo, searchName, sorting, start, end);
        Integer countOfPreview = boardDao.countPreview(townNo, searchName);
        Integer countOfPage = (countOfPreview-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer startPage = prev+1;
        Integer endPage = prev + BLOCKSIZE;
        Integer next = endPage+1;
        if(end>=countOfPage) {
            endPage = countOfPage;
            next = 0;
        }
        return new BoardDto.PaginationPreview(pageNo, prev, startPage, endPage, next, preview);
    }


    public BoardDto.Read readByBoardNo(Integer boardNo){
        return boardDao.readByBoardNo(ImageConstants.IMAGE_PROFILE_URL,boardNo);
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

