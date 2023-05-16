package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.entity.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {
    // 큐엔에이 저장
    public Integer save(Qna qna);

    // 특정 상품의 큐엔에이 개수를 조회
    public Integer count(Integer productNo);

    // 상품 큐엔에이 보기
    public List<Qna> findByProductNo(Integer productNo);

    // 특정 상품의 큐엔에이 목록 조회

    public List<QnaDto.QnaList> findAllByProductNo(Integer startRowNum, Integer endRowNum, Integer productNo);

    public List<QnaDto.Read> findAll();

    public Integer update(Qna qna);

    public QnaDto.Read findByQnaNo(Integer qnaNo);

    public List<QnaDto.FindByMemberNo> findByMemberNo(Integer startRowNum, Integer endRowNum,Integer memberNo);

    public Integer countMe(Integer memberNo);

}
