package kr.kro.namohagae.board.dao;

import kr.kro.namohagae.board.dto.KnowledgeMainDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeQuestionDao {
   public Integer save(KnowledgeQuestion knowledgeQuestion);

    public int update();

    public void insertLike(Integer boardNo, Integer memberNo);

    public KnowledgeQuestionDto.Read findByKnowledgeQuestionNo(String url, Integer knowledgeQuestionNo);

    public List<KnowledgeQuestionDto.Preview> findAll(Integer startRowNum, Integer endRowNum);

    public Integer count();
    public List<KnowledgeQuestionDto.myQuestionList> findAllByMemberNo(Integer startRowNum, Integer endRowNum, Integer memberNo);

    public Integer countByMemberNo(Integer memberNo);

    public List<KnowledgeMainDto> readList();

    public List<KnowledgeMainDto> readList2();

    public List<KnowledgeMainDto> waitList(@Param("searchName") String searchName, @Param("start") int start, @Param("limit") int limit);

    public Integer delete(Integer knowledgeQuestionNo);

    public Integer findMemberNoByBoardNo(Integer boardNo);
}
