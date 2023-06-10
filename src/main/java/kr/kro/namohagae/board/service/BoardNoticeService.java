package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardNoticeDao;
import kr.kro.namohagae.board.dto.BoardNoticeDto;
import kr.kro.namohagae.board.entity.BoardNotice;
import kr.kro.namohagae.global.util.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardNoticeService {

    private final BoardNoticeDao boardNoticeDao;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public void addNotice(BoardNoticeDto.Add dto){
        BoardNotice boardNotice = dto.toEntity();
        boardNoticeDao.save(boardNotice);
    }
    public List<BoardNoticeDto.Preview> preview() {
        return boardNoticeDao.preview();
    }
    public BoardNoticeDto.Pagination list(Integer pageNo) {
        Integer countOfNotice = boardNoticeDao.count();
        Pagination page = new Pagination(BLOCKSIZE, PAGESIZE, pageNo, countOfNotice);
        List<BoardNoticeDto.Preview> notice =  boardNoticeDao.noticeList(page.getStartRowNum());
        return new BoardNoticeDto.Pagination(pageNo, page.getPrevPage(), page.getStartPage(), page.getEndPage(), page.getNextPage(), notice);
    }

    public BoardNoticeDto.Read findByBoardNoticeNo(Integer boardNoticeNo) {
        return boardNoticeDao.findByBoardNoticeNo(boardNoticeNo);
    }
    public Integer delete(Integer boardNoticeNo){
        return boardNoticeDao.delete(boardNoticeNo);
    }

    public void update(BoardNoticeDto.Update noticeDto){
        BoardNotice boardNotice = noticeDto.toEntity();
        boardNoticeDao.update(boardNotice);
    }
}
