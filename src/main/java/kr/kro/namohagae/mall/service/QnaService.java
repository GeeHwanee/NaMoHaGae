package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.QnaDao;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QnaService {
    private final QnaDao qnaDao;
    private final ProductDao productDao;

    @Transactional
    public void write(QnaDto.Write dto, String memberEmail) {
        Qna qna = dto.toEntity(memberEmail);
        qnaDao.save(qna);
        //productDao.updateQna(qna);
    }

}
