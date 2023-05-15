package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.KnowledgeAnswerDao;
import kr.kro.namohagae.board.dao.KnowledgeQuestionDao;
import kr.kro.namohagae.board.dto.KnowledgeDto;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KnowledgeService {

    private final KnowledgeQuestionDao knowledgeQuestionDao;
    private final KnowledgeAnswerDao knowledgeAnswerDao;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public Integer save(KnowledgeDto.Write dto, Integer memberNo){
        KnowledgeQuestion knowledgeQuestion = dto.toEntity(memberNo);
        knowledgeQuestionDao.save(knowledgeQuestion);
        return knowledgeQuestion.getKnowledgeQuestionNo();
    }


    public KnowledgeDto.Read read(Integer knowledgeQuestionNo) {
        knowledgeQuestionDao.update(knowledgeQuestionNo);
        return knowledgeQuestionDao.findByKnowledgeQuestionNo(knowledgeQuestionNo);
    }

    public KnowledgeDto.Pagination findAll(Integer pageNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeDto.List> questions = knowledgeQuestionDao.findAll(startRowNum, endRowNum);
        for (KnowledgeDto.List list:
             questions) {
            System.out.println(list.toString());
        }
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
        return new KnowledgeDto.Pagination(pageNo, prev, start, end, next, questions);
    }
}
