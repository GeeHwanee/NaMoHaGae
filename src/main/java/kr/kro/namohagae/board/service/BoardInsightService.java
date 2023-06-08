package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dao.BoardInsightDao;
import kr.kro.namohagae.board.dao.KnowledgeAnswerDao;
import kr.kro.namohagae.board.dao.KnowledgeQuestionDao;
import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.BoardType;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardInsightService {

    private final MemberDao memberDao;
    private final BoardDao boardDao;
    private final KnowledgeQuestionDao knowledgeQuestionDao;
    private final KnowledgeAnswerDao knowledgeAnswerDao;
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
    @Transactional
    public Boolean updateBoardRecommendEnabled(BoardType boardType, Integer boardNo, Integer memberNo){

        String link ="";
        Member member = null;

        switch (boardType){
            case FREE -> {
                member = memberDao.findByMemberNo(boardDao.findMemberNoByBoardNo(boardNo)).get();
                link = NotificationConstants.BOARD_FREE_LINK+boardNo;
            }case TOWN -> {
                member = memberDao.findByMemberNo(boardDao.findMemberNoByBoardNo(boardNo)).get();
                link = NotificationConstants.BOARD_TOWN_LINK+boardNo;
            }case KNOWLEDGE_QUESTION -> {
                member = memberDao.findByMemberNo(knowledgeQuestionDao.findMemberNoByBoardNo(boardNo)).get();
                link = NotificationConstants.BOARD_KNOWLEDGE_LINK+boardNo;
            }case KNOWLEDGE_ANSWER -> {
                KnowledgeAnswer knowledgeAnswer = knowledgeAnswerDao.findByKnowledgeAnswerNo(boardNo);
                member = memberDao.findByMemberNo(knowledgeAnswer.getMemberNo()).get();
                link = NotificationConstants.BOARD_KNOWLEDGE_LINK +knowledgeAnswer.getKnowledgeQuestionNo();
            }
        }
        boardInsightDao.updateBoardRecommendEnabled(boardNo, memberNo);
        Boolean result = boardInsightDao.findBoardRecommendEnabledByBoardNoAndMemberNo(boardNo,memberNo);
        if(result) {
            notificationService.save(member, NotificationConstants.RECOMMEND_CONTENT, link);
        }
        return result;
    }
}
