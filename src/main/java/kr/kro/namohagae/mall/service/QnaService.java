package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.QnaDao;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {
    private final QnaDao qnaDao;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    @Transactional
    public void write(QnaDto.Write dto, String memberEmail) {
        Qna qna = dto.toEntity(memberEmail);
        qnaDao.save(qna);
    }

    public QnaDto.Pagination list(Integer pageNo, Integer productNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<Qna> qnas =  qnaDao.findAllByProductNo(startRowNum, endRowNum, productNo);
        Integer countOfQna = qnaDao.count(productNo);
        Integer countOfPage = (countOfQna-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new QnaDto.Pagination(pageNo, prev, start, end, next, productNo, qnas);
    }
}
