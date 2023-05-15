package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KnowledgeAnswerDao {
    public Integer save(KnowledgeAnswer knowledgeAnswer);
}
