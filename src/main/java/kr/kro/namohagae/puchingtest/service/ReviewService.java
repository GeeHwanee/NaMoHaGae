package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.puchingtest.dao.ReviewDao;
import kr.kro.namohagae.puchingtest.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    private final static Integer PAGESIZE=5;
    private final static Integer BLOCKSIZE=3;
    public ReviewDto.Pagination findContent(Integer pageno,Integer memberNo) {
        Integer countOfProduct = reviewDao.count(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<ReviewDto.profile> review = reviewDao.findContentByReceiverNo(startRownum, endRownum,memberNo);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ReviewDto.Pagination(pageno, prev, start, end, next, review);

    }
}
