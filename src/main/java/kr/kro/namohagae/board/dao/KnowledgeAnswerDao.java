package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.KnowledgeAnswerDto;
import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeAnswerDao {
    public Integer save(KnowledgeAnswer knowledgeAnswer);

    public List<KnowledgeAnswerDto.Read> findAll(Integer questionNo);

    public Integer update(Integer answerNo);
    public KnowledgeAnswer findByKnowledgeAnswerNo(Integer answerNo);

    public List<KnowledgeAnswerDto.myAnswerList> findAllByMemberNo(Integer startRowNum, Integer endRowNum, Integer memberNo);

    public Integer countByMemberNo(Integer memberNo);

    public void delete(Integer answerNo);
}
