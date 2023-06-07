package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardInsightDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardInsightService {

    private final BoardInsightDao boardInsightDao;

    public Boolean existsByBoardNoAndMemberNo(Integer boardNo, Integer memberNo){
        return boardInsightDao.existsByBoardNoAndMemberNo(boardNo, memberNo);
    }

    public Boolean findBoardLikeEnabled(Integer boardNo, Integer memberNo){
        return boardInsightDao.findBoardLikeEnabledByBoardNoAndMemberNo(boardNo, memberNo);
    }

    public void updateBoardLikeEnabled(Integer boardNo, Integer memberNo){
        boardInsightDao.updateBoardLikeEnabled(boardNo, memberNo);
    }
}
