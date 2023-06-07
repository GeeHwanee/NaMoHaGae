package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.KnowledgeAnswerDao;
import kr.kro.namohagae.board.dao.KnowledgeQuestionDao;
import kr.kro.namohagae.board.dto.KnowledgeAnswerDto;
import kr.kro.namohagae.board.dto.KnowledgeMainDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    int pageLimit = 10; // 한 페이지당 보여줄 글 갯수
    int blockLimit = 5; // 하단에 보여줄 페이지 번호 갯수
    public Integer questionSave(KnowledgeQuestionDto.Write dto, Integer memberNo) {
        Integer memberPoint = memberDao.findByMemberNo(memberNo).get().getMemberPoint();
        if (memberPoint >= dto.getKnowledgeQuestionPoint()) {
            Integer negativePoint = -dto.getKnowledgeQuestionPoint();
            KnowledgeQuestion knowledgeQuestion = dto.toEntity(memberNo);
            knowledgeQuestionDao.save(knowledgeQuestion);
            memberDao.updatePoint(memberNo, negativePoint);
            return knowledgeQuestion.getKnowledgeQuestionNo();
        } else {
            return 0;
        }

    }

    public void insertLike(Integer boardNo, Integer memberNo) {

        knowledgeQuestionDao.insertLike(boardNo, memberNo);
    }
    public KnowledgeQuestionDto.Read questionRead(Integer knowledgeQuestionNo) {

        return knowledgeQuestionDao.findByKnowledgeQuestionNo(knowledgeQuestionNo);
    }
    public int update() {

        return knowledgeQuestionDao.update();
    }

    public KnowledgeQuestionDto.Pagination questionFindAll(Integer pageNo) {
        Integer startRowNum = (pageNo - 1) * PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeQuestionDto.Preview> questions = knowledgeQuestionDao.findAll(startRowNum, endRowNum);
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
    @Transactional
    public Boolean answerSave(KnowledgeAnswerDto.Write dto, Integer memberNo) {
        Integer isWrited = knowledgeAnswerDao.existsByMemberNo(dto.getKnowledgeQuestionNo(),memberNo);
        if (dto.getQuestionMemberNo().equals(memberNo)){
            return false;
        }
       if (isWrited>0) {
            return false;
        }
        Member member = memberDao.findByMemberNo(dto.getQuestionMemberNo()).get();
        KnowledgeAnswer knowledgeAnswer = dto.toEntity(memberNo);
        Integer result = knowledgeAnswerDao.save(knowledgeAnswer);
        if (result==1){
            notificationService.save(member, NotificationConstants.KNOWLEDGE_CONTENT,NotificationConstants.BOARD_KNOWLEDGE_LINK+dto.getKnowledgeQuestionNo());
            return true;
        }else {
            return false;
        }
    }


    public List<KnowledgeAnswerDto.Read> answerFindAll(Integer questionNo) {
        return knowledgeAnswerDao.findAll(questionNo);
    }

    @Transactional
    public Boolean answerUpdate(Integer answerNo, Integer point) {
        KnowledgeAnswer knowledgeAnswer = knowledgeAnswerDao.findByKnowledgeAnswerNo(answerNo);
        memberDao.updatePoint(knowledgeAnswer.getMemberNo(), point);
        Integer result = knowledgeAnswerDao.update(answerNo);

        if(result==1){
            Member member = memberDao.findByMemberNo(knowledgeAnswer.getMemberNo()).get();
            notificationService.save(member, NotificationConstants.KNOWLEDGE_SELECT_CONTENT, NotificationConstants.BOARD_KNOWLEDGE_LINK+knowledgeAnswer.getKnowledgeQuestionNo());
            return true;
        }else {
            return false;
        }
    }

    public Boolean answerDelete(Integer answerNo) {
        KnowledgeAnswer knowledgeAnswer = knowledgeAnswerDao.findByKnowledgeAnswerNo(answerNo);
        if(knowledgeAnswer.getKnowledgeAnswerSelectionEnabled()){
            return false;
        }else {
            knowledgeAnswerDao.delete(answerNo);
            return true;
        }

    }

    public KnowledgeQuestionDto.myPagination myQusetionList(Integer pageno, Integer memberNo) {
        Integer startRowNum = (pageno - 1) * PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeQuestionDto.myQuestionList> myQusetionList = knowledgeQuestionDao.findAllByMemberNo(startRowNum, endRowNum, memberNo);
        Integer countOfFavorite = knowledgeQuestionDao.countByMemberNo(memberNo);
        Integer countOfPage = (countOfFavorite - 1) / PAGESIZE + 1;
        Integer prev = (pageno - 1) / BLOCKSIZE * BLOCKSIZE;
        Integer start = prev + 1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end + 1;
        if (end >= countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new KnowledgeQuestionDto.myPagination(pageno, prev, start, end, next, myQusetionList);
    }

    public KnowledgeAnswerDto.myAnswerPagination myAnswerList(Integer pageno, Integer memberNo) {
        Integer startRowNum = (pageno - 1) * PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<KnowledgeAnswerDto.myAnswerList> myAnswerLists = knowledgeAnswerDao.findAllByMemberNo(startRowNum, endRowNum, memberNo);
        Integer countOfFavorite = knowledgeAnswerDao.countByMemberNo(memberNo);
        Integer countOfPage = (countOfFavorite - 1) / PAGESIZE + 1;
        Integer prev = (pageno - 1) / BLOCKSIZE * BLOCKSIZE;
        Integer start = prev + 1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end + 1;
        if (end >= countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new KnowledgeAnswerDto.myAnswerPagination(pageno, prev, start, end, next, myAnswerLists);
    }
    public List<KnowledgeMainDto> readList() {

        return knowledgeQuestionDao.readList();
    }

    public List<KnowledgeMainDto> readList2() {

        return knowledgeQuestionDao.readList2();
    }



    public List<KnowledgeMainDto> waitList(String searchName,int page) {


        int pagingStart = (page - 1) * pageLimit;

        return knowledgeQuestionDao.waitList(searchName,pagingStart, pageLimit);
    }

    public Integer questionDelete(Integer knowledgeQuestionNo) {
       return knowledgeQuestionDao.delete(knowledgeQuestionNo);
    }
}


