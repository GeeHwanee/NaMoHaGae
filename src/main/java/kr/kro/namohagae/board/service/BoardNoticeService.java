package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardNoticeDao;
import kr.kro.namohagae.board.dto.NoticeDto;
import kr.kro.namohagae.board.entity.BoardNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardNoticeService {
    @Autowired
    private BoardNoticeDao boardNoticeDao;

    public void addNotice(NoticeDto.Add dto){
        BoardNotice boardNotice = dto.toEntity();
        boardNoticeDao.save(boardNotice);


    }


    public List<BoardNotice> list() {
        return boardNoticeDao.findAll();
    }
}
