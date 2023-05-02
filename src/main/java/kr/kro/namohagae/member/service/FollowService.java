package kr.kro.namohagae.member.service;

import kr.kro.namohagae.member.dao.FollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FollowService {
    @Autowired
    private FollowDao followDao;


    public Boolean checkFollow(Integer memberNo,Integer myMemberNo){
        return  followDao.existsByMemberNoAndFollowMemberNo(memberNo,myMemberNo);
    }

    public Map<String,Boolean> follow(Integer memberNo, Integer myMemberNo) {

        Boolean alreadyFollow = followDao.existsByMemberNoAndFollowMemberNo(memberNo,myMemberNo);
        Map<String,Boolean> map = new HashMap<>();
        if (alreadyFollow==false) {
            followDao.save(memberNo,myMemberNo);
            map.put("follow", true);
        } else {
            followDao.delete(memberNo,myMemberNo);
            map.put("follow", false);
        }
        return map;
    }
}
