package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    BoardDao boardDao;

    @Autowired
    private MemberDao memberDao;

    public void boardFreeInsertData(BoardDto.write boardDto, String userEmail) {


        Board board = boardDto.toEntity(memberDao.findNoByUsername(userEmail), boardDto.getTitle(), boardDto.getContent());

        // Set other properties of board
         boardDao.boardFreeInsertData(board);
    }

    public List<Board> boardFreeList() {
        return boardDao.boardFreeList();
    }

    public Board boardFreeReadData(Integer boardNo) {

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
    public List<Board> pagingList(int page) {
        
        /*
        1페이지당 보여지는 글 갯수 5
            1page => 0
            2page => 5
            3page => 10
         */
        int pagingStart = (page - 1) * pageLimit;

        return boardDao.pagingList(pagingStart,pageLimit);
    }
        public PageDto pagingParam(int page) {
            // 전체 글 갯수 조회
            int boardCount = boardDao.boardCount();
            // 전체 페이지 갯수 계산(10/3=3.33333 => 4)
            int maxPage = (int) (Math.ceil((double) boardCount / pageLimit));
            // 시작 페이지 값 계산(1, 4, 7, 10, ~~~~)
            int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
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

        public Integer increaseReadCnt(Integer boardNo) {

        return boardDao.increaseReadCnt(boardNo);
        }

    }

