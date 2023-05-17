package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardTownDao;
import kr.kro.namohagae.board.dto.BoardTownDto;
import kr.kro.namohagae.board.dto.BoardTownListDto;
import kr.kro.namohagae.board.dto.PageDto;
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
    private final static Integer PAGESIZE = 10;
    private final static Integer BLOCKSIZE =5;

    public void boardTownInsertData(BoardTownDto.write boardTownDto, String userEmail) {

        Board board = boardTownDto.toEntity(boardTownDto.getTownNo(),memberDao.findNoByUsername(userEmail), boardTownDto.getTitle(), boardTownDto.getContent());
        System.out.println("왜안되 이거"+board);
        boardTownDao.boardTownInsertData(board);
    }

    public List<BoardTownListDto> boardTownList(Integer townNo,String searchName,int page) {
        int pageLimit = 10;
        int pagingStart = (page - 1) * pageLimit;
        return boardTownDao.boardTownList(townNo,searchName,pagingStart, pageLimit);

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
