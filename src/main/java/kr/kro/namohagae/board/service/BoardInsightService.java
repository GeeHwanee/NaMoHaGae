package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardInsightDao;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardInsightService {

    private final MemberDao memberDao;
    private final BoardInsightDao boardInsightDao;
    private final NotificationService notificationService;

    public Integer save(Integer boardNo, Integer memberNo){
        return boardInsightDao.save(boardNo, memberNo);
    }

    public Boolean existsByBoardNoAndMemberNo(Integer boardNo, Integer memberNo){
        return boardInsightDao.existsByBoardNoAndMemberNo(boardNo, memberNo);
    }

    public Boolean findBoardRecommendEnabled(Integer boardNo, Integer memberNo){
        return boardInsightDao.findBoardRecommendEnabledByBoardNoAndMemberNo(boardNo, memberNo);
    }

    public void updateBoardRecommendEnabled(Integer boardNo, Integer boardMemberNo, Integer memberNo){
        boardInsightDao.updateBoardRecommendEnabled(boardNo, memberNo);
    }
}
