package kr.kro.namohagae.member.service;

import kr.kro.namohagae.global.service.NotificationService;
import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.global.util.constants.NotificationConstants;
import kr.kro.namohagae.member.dao.FollowDao;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.FollowDto;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowDao followDao;
    private final MemberDao memberDao;
    private final NotificationService notificationService;

    private final Integer PAGESIZE = 10;
    private final Integer BLOCKSIZE = 5;

    public Boolean checkFollow(Integer memberNo,Integer myMemberNo){
        return  followDao.existsByMemberNoAndFollowMemberNo(memberNo,myMemberNo);
    }

    public Map<String,Boolean> follow(Integer memberNo, Integer myMemberNo) {
        Member member = memberDao.findByMemberNo(memberNo).get();
        Member followMember = memberDao.findByMemberNo(myMemberNo).get();
        Boolean alreadyFollow = followDao.existsByMemberNoAndFollowMemberNo(memberNo,myMemberNo);
        Map<String,Boolean> map = new HashMap<>();
        if (alreadyFollow==false) {
            followDao.save(memberNo,myMemberNo);
            map.put("follow", true);
            notificationService.save(member,followMember.getMemberNickname()+NotificationConstants.FOLLOW_CONTENT, NotificationConstants.PROFILE_LINK+myMemberNo);
        } else {
            followDao.delete(memberNo,myMemberNo);
            map.put("follow", false);
        }
        return map;
    }

    public FollowDto.Pagination list(Integer pageNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<FollowDto.FollowList> follow =  followDao.findFollowList(startRowNum, endRowNum, memberNo,ImageConstants.IMAGE_PROFILE_URL);
        Integer countOfFavorite = followDao.count(memberNo);
        Integer countOfPage = (countOfFavorite-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {

            end = countOfPage;
            next = 0;
        }
        return new FollowDto.Pagination(pageNo, prev, start, end, next, follow);
    }
}
