package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardNoticeDao;
import kr.kro.namohagae.board.dto.BoardNoticeDto;
import kr.kro.namohagae.board.entity.BoardNotice;
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
        Integer start = (pageNo-1)*PAGESIZE + 1;
        Integer end = start + PAGESIZE - 1;
        List<BoardNoticeDto.Preview> notice =  boardNoticeDao.noticeList(start);
        Integer countOfFavorite = boardNoticeDao.count();
        Integer countOfPage = (countOfFavorite-1)/PAGESIZE + 1;
        Integer prev = ((pageNo-1)/BLOCKSIZE) * BLOCKSIZE;
        Integer startPage = prev+1;
        Integer endPage = prev + BLOCKSIZE;
        Integer next = endPage+1;
        if(endPage>=countOfPage) {

            endPage = countOfPage;
            next = 0;
        }
        System.out.println(start);
        System.out.println(end);
        System.out.println(startPage);
        System.out.println(endPage);
//        System.out.println(notice.get(0).getBoardNoticeNo());
//        System.out.println(notice.get(0).getBoardNoticeWriter());
//        System.out.println(notice.get(0).getBoardNoticeWriteDate());
        return new BoardNoticeDto.Pagination(pageNo, prev, startPage, endPage, next, notice);
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
