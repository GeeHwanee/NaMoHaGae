package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.QnaDao;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.entity.Qna;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {
    private final QnaDao qnaDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final NotificationService notificationService;

    private Integer PAGESIZE = 10;
    private Integer BLOCKSIZE = 5;

    @Transactional
    public void write(QnaDto.Write dto, Integer memberNo) {
        Qna qna = dto.toEntity(memberNo);
        qnaDao.save(qna);
    }

    public ProductDto.Read read(Integer productNo) {
        return productDao.findByProductNo(ImageConstants.IMAGE_PRODUCT_URL,productNo);
    }

    public QnaDto.Pagination list(Integer pageNo, Integer productNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<QnaDto.QnaList> qna =  qnaDao.findAllByProductNo(startRowNum, endRowNum, productNo);
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
        return new QnaDto.Pagination(pageNo, prev, start, end, next, productNo, qna);
    }

    public List<QnaDto.Read> readAll(){
        return qnaDao.findAll(ImageConstants.IMAGE_PRODUCT_URL);
    }
    public Integer update(QnaDto.Put dto){
        Qna qna = dto.toEntity();
        Member member = memberDao.findByMemberNo(qna.getQnaWriter()).get();
        qnaDao.update(qna);
        notificationService.save(member, NotificationConstants.QNA_CONTENT,NotificationConstants.QNA_LINK);
        return 1;
    }

    public QnaDto.Read print(Integer qnaNo) {
        return qnaDao.findByQnaNo(ImageConstants.IMAGE_PRODUCT_URL, ImageConstants.IMAGE_PROFILE_URL, qnaNo);
    }
    public QnaDto.PaginationMyQna listMyQna(Integer pageNo, Integer memberNo) {
        PAGESIZE = 5;
        BLOCKSIZE = 3;
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<QnaDto.FindByMemberNo> qna =  qnaDao.findByMemberNo(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, memberNo);
        Integer countOfQna = qnaDao.countMe(memberNo);
        Integer countOfPage = (countOfQna-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new QnaDto.PaginationMyQna(pageNo, prev, start, end, next, qna);
    }

    public QnaDto.MyQnA findAnswerByQnaNo(Integer qnaNo){
        return qnaDao.findAnswerByQnaNo(qnaNo);
    }
}
