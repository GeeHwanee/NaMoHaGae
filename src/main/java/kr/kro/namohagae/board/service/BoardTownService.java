package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardTownService {

    @Autowired
    BoardTownDao boardTownDao;

    public void boardTownInsertData(Board board) {

        boardTownDao.boardTownInsertData(board);
    }

    public List<Board> boardTownList(Integer townNo) {

        return boardTownDao.boardTownList(townNo);
    }
}
