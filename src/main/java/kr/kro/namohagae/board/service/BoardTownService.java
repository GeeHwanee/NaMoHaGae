package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardTownService {

    @Autowired
    BoardTownDao boardTownDao;

    private final static Integer PAGESIZE = 10;
    private final static Integer BLOCKSIZE =5;

    public void boardTownInsertData(Board board) {

        boardTownDao.boardTownInsertData(board);
    }

    public List<Board> boardTownList(Integer townNo) {


        return boardTownDao.boardTownList(townNo);

    }

    public Board boardTownRead(Integer boardNo) {

        return boardTownDao.boardTownRead(boardNo);
    }

    public Integer townReadCnt(Integer boardNo) {

        return boardTownDao.townReadCnt(boardNo);
    }

    public void townUpdateData(Board board) {

        boardTownDao.townUpdateData(board);
    }

    public Integer townDeleteData(Integer boardNo) {

        return boardTownDao.townDeleteData(boardNo);
    }
}
