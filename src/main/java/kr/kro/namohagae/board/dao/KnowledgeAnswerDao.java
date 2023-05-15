package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.KnowledgeAnswerDto;
import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeAnswerDao {
    public Integer save(KnowledgeAnswer knowledgeAnswer);

    public List<KnowledgeAnswerDto.Read> findAll(Integer questionNo);
}
