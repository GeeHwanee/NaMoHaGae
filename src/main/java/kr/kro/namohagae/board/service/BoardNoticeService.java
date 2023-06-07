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

    public void addNotice(BoardNoticeDto.Add dto){
        BoardNotice boardNotice = dto.toEntity();
        boardNoticeDao.save(boardNotice);
    }
    public List<BoardNoticeDto.Preview> preview() {
        return boardNoticeDao.preview();
    }

    public BoardNotice findByBoardNoticeNo(Integer boardNoticeNo) {

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
