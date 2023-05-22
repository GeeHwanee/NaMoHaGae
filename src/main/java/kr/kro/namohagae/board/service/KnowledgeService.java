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
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class KnowledgeService {

    private final KnowledgeQuestionDao knowledgeQuestionDao;
    private final KnowledgeAnswerDao knowledgeAnswerDao;
    private final MemberDao memberDao;
    private final NotificationService notificationService;
    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public Integer questionSave(KnowledgeQuestionDto.Write dto, Integer memberNo) {
        Integer point = memberDao.findByMember(memberNo).get().getMemberPoint();
        if (point >= dto.getKnowledgeQuestionPoint()) {
            KnowledgeQuestion knowledgeQuestion = dto.toEntity(memberNo);
            knowledgeQuestionDao.save(knowledgeQuestion);
            memberDao.updatePoint(memberNo, (-point));
            return knowledgeQuestion.getKnowledgeQuestionNo();
        } else {
            return 0;
        }

    }


    public KnowledgeQuestionDto.Read questionRead(Integer knowledgeQuestionNo) {
        knowledgeQuestionDao.update(knowledgeQuestionNo);
        return knowledgeQuestionDao.findByKnowledgeQuestionNo(knowledgeQuestionNo);
    }

    public KnowledgeQuestionDto.Pagination questionFindAll(Integer pageNo) {
        Integer startRowNum = (pageNo - 1) * PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeQuestionDto.List> questions = knowledgeQuestionDao.findAll(startRowNum, endRowNum);
        Integer countOfQuestion = knowledgeQuestionDao.count();
        Integer countOfPage = (countOfQuestion - 1) / PAGESIZE + 1;
        Integer prev = (pageNo - 1) / BLOCKSIZE * BLOCKSIZE;
        Integer start = prev + 1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end + 1;
        if (end >= countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new KnowledgeQuestionDto.Pagination(pageNo, prev, start, end, next, questions);
    }

    public Boolean answerSave(KnowledgeAnswerDto.Write dto, Integer memberNo) {
        if (Objects.equals(dto.getAnswerMemberNo(), memberNo))
            return false;

        KnowledgeAnswer knowledgeAnswer = dto.toEntity(memberNo);
        return knowledgeAnswerDao.save(knowledgeAnswer) == 1;
    }


    public List<KnowledgeAnswerDto.Read> answerFindAll(Integer questionNo) {
        return knowledgeAnswerDao.findAll(questionNo);
    }

    public Boolean answerUpdate(Integer answerNo) {
        KnowledgeAnswerDto.Point point = knowledgeAnswerDao.findByKnowledgeAnswerNo(answerNo);
        memberDao.updatePoint(point.getKnowledgeAnswerMemberNo(), point.getKnowledgeQuestionPoint());
        knowledgeAnswerDao.update(answerNo);
        return true;
    }

    public KnowledgeQuestionDto.myPagination myQusetionList(Integer pageno, Integer memberNo) {
        Integer startRowNum = (pageno-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeQuestionDto.myQuestionList> myQusetionList =  knowledgeQuestionDao.findAllByMemberNo(startRowNum,endRowNum, memberNo);
        Integer countOfFavorite = knowledgeQuestionDao.countByMemberNo(memberNo);
        Integer countOfPage = (countOfFavorite-1)/PAGESIZE + 1;
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new KnowledgeQuestionDto.myPagination(pageno, prev, start, end, next, myQusetionList);
    }

}

