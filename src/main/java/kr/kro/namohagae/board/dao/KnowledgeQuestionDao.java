package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.KnowledgeDto;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeQuestionDao {
   public Integer save(KnowledgeQuestion knowledgeQuestion);

    public void update(Integer knowledgeQuestionNo);

    public KnowledgeDto.Read findByKnowledgeQuestionNo(Integer knowledgeQuestionNo);

    public List<KnowledgeDto.List> findAll(Integer startRowNum, Integer endRowNum);

    public Integer count();
}
