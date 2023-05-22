package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeQuestionDao {
   public Integer save(KnowledgeQuestion knowledgeQuestion);

    public void update(Integer knowledgeQuestionNo);

    public KnowledgeQuestionDto.Read findByKnowledgeQuestionNo(Integer knowledgeQuestionNo);

    public List<KnowledgeQuestionDto.List> findAll(Integer startRowNum, Integer endRowNum);

    public Integer count();
    public List<KnowledgeQuestionDto.myQuestionList> findAllByMemberNo(Integer startRowNum, Integer endRowNum, Integer memberNo);

    public Integer countByMemberNo(Integer memberNo);

}
