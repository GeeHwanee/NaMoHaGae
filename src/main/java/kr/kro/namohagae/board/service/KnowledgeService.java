package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.KnowledgeAnswerDao;
import kr.kro.namohagae.board.dao.KnowledgeQuestionDao;
import kr.kro.namohagae.board.dto.KnowledgeAnswerDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KnowledgeService {

    private final KnowledgeQuestionDao knowledgeQuestionDao;
    private final KnowledgeAnswerDao knowledgeAnswerDao;
    private final MemberDao memberDao;
    private final NotificationService notificationService;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public Integer questionSave(KnowledgeQuestionDto.Write dto, Integer memberNo){
        KnowledgeQuestion knowledgeQuestion = dto.toEntity(memberNo);
        knowledgeQuestionDao.save(knowledgeQuestion);
        return knowledgeQuestion.getKnowledgeQuestionNo();
    }


    public KnowledgeQuestionDto.Read questionRead(Integer knowledgeQuestionNo) {
        knowledgeQuestionDao.update(knowledgeQuestionNo);
        return knowledgeQuestionDao.findByKnowledgeQuestionNo(knowledgeQuestionNo);
    }

    public KnowledgeQuestionDto.Pagination questionFindAll(Integer pageNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeQuestionDto.List> questions = knowledgeQuestionDao.findAll(startRowNum, endRowNum);
        Integer countOfQuestion = knowledgeQuestionDao.count();
        Integer countOfPage = (countOfQuestion-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new KnowledgeQuestionDto.Pagination(pageNo, prev, start, end, next, questions);
    }

    public Boolean answerSave(KnowledgeAnswerDto.Write dto, Integer memberNo){
        KnowledgeAnswer knowledgeAnswer = dto.toEntity(memberNo);
        return knowledgeAnswerDao.save(knowledgeAnswer)==1;
    }


    public void answerFindAll(Integer questionNo) {

    }
}
