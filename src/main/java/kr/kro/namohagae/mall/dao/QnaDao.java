package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.entity.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {
    // 큐엔에이 저장
    public Integer save(Qna qna);

    // 상품 큐엔에이 보기
    public List<Qna> findByProductNo(Integer productNo);
}
