package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dao.BoardNoticeDao;
import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.global.util.pagination.Pagination;
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

    public List<BoardDto.Preview> mainPreview(Integer townNo, String sorting) {
       return boardDao.preview(townNo,null,null, sorting,1,5 );
    }
    public BoardDto.Pagination preview(Integer townNo, Integer memberNo, String searchName, String sorting, Integer pageNo) {
        System.out.println(memberNo);
        Integer countOfPreview = boardDao.countPreview(townNo, memberNo, searchName);
        Pagination page = new Pagination(BLOCKSIZE,PAGESIZE,pageNo,countOfPreview);
        List<BoardDto.Preview> preview =  boardDao.preview(townNo, memberNo, searchName, sorting, page.getStartRowNum(),10);
        return new BoardDto.Pagination(pageNo, page.getPrevPage(), page.getStartPage(), page.getEndPage(), page.getNextPage(), preview);
    }


    public BoardDto.Read readByBoardNo(Integer boardNo){
        return boardDao.readByBoardNo(ImageConstants.IMAGE_PROFILE_URL,boardNo);
    }
    public Boolean findBoardEnabledByBoardNo(Integer boardNo) {
        return boardDao.findBoardEnabledByBoardNo(boardNo);
    }

    public void boardUpdateData(Board board) {

        boardDao.boardUpdateData(board);
    }


    public void boardDelete(Integer boardNo) {
        boardDao.delete(boardNo);
    }

}

