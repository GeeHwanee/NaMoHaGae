package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.QnaDao;
import kr.kro.namohagae.mall.dto.ProductDto;
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
    private final ProductDao productDao;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    @Transactional
    public void write(QnaDto.Write dto, Integer memberNo) {
        Qna qna = dto.toEntity(memberNo);
        qnaDao.save(qna);
    }

    public ProductDto.Read read(Integer productNo) {
        return productDao.findByProductNo(productNo);
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

    public List<QnaDto.Read> readAll(){
        return qnaDao.findAll();
    }
    public Integer update(QnaDto.Put dto){
        Qna qna = dto.toEntity();
        return qnaDao.update(qna);
    }

    public QnaDto.Read print(Integer qnaNo) {
        return qnaDao.findByQnaNo(qnaNo);
    }
}
