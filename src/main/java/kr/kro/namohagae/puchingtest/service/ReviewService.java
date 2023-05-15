package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dao.Puchingdao;
import kr.kro.namohagae.puchingtest.dao.ReviewDao;
import kr.kro.namohagae.puchingtest.dto.ReviewDto;
import kr.kro.namohagae.puchingtest.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private Puchingdao puchingdao;


    private final static Integer PAGESIZE=5;
    private final static Integer BLOCKSIZE=3;
    public ReviewDto.PaginationProfie findContentByReceiverNo(Integer pageno,Integer memberNo) {
        Integer countOfProduct = reviewDao.countReceiver(memberNo);
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
        return new ReviewDto.PaginationProfie(pageno, prev, start, end, next, review);
    }
    public ReviewDto.PaginationImfo findContentByWriterNo(Integer pageno,Integer memberNo) {
        Integer countOfProduct = reviewDao.countWriter(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<ReviewDto.Imformation> review = reviewDao.findContentByWriterNo(startRownum, endRownum,memberNo);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ReviewDto.PaginationImfo(pageno, prev, start, end, next, review);
    }

    public ReviewDto.writeview findWriteViewInfo(String userEmail,Integer receiverNo,Integer puchingNo){
        Integer userNo=memberDao.findNoByUsername(userEmail);
        ReviewDto.writeview dto = reviewDao.reviewWriteInfo(userNo,receiverNo,puchingNo);

        return dto;
    };


    public void saveReview(String username,ReviewDto.write dto){
        Integer memberNo=memberDao.findNoByUsername(username);
        Review review= dto.toEntity(memberNo);
        Integer bonePoint=300;
        System.out.println(review);
            reviewDao.save(review);
            reviewDao.updateMemberPoint(bonePoint,memberNo);
            reviewDao.updateMemberGrade(dto.getStarPoint(),dto.getReceiverNo());
        if(reviewDao.checkCountReviewByPuchingNo(dto.getPuchingNo())>=2) {  //   퍼칭번호로 작성된 리뷰글 숫자 체크 이거의 여부에 따라 리뷰작성후 상태랑 콘텐트타입을 바꾼다
            reviewDao.updatePuchingStatus(dto.getPuchingNo());
            reviewDao.updateMessageContentType(dto.getPuchingNo());
        }

    };
}
