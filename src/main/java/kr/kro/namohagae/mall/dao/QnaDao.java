package kr.kro.namohagae.mall.dao;

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
    public List<Qna> findAllByProductNo(Integer startRowNum, Integer endRowNum, Integer productNo);
}
