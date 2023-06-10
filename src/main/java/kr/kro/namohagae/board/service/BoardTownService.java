package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.dto.BoardMainList;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardTownService {

    @Autowired
    BoardTownDao boardTownDao;
    @Autowired
    MemberDao memberDao;


    public List<BoardMainList> mainReadList(){

        return boardTownDao.mainReadList();
    }

    public List<BoardMainList> mainRecommendList(){

        return boardTownDao.mainRecommendList();
    }


    public void townUpdateData(Board board) {

        boardTownDao.townUpdateData(board);
    }

}
