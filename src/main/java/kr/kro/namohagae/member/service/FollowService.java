package kr.kro.namohagae.member.service;

import kr.kro.namohagae.member.dao.FollowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowDao followDao;

    public Boolean save(Integer followMemberNo,Integer memberNo){
        return followDao.save(followMemberNo,memberNo);
    }

    public Boolean delete(Integer followMemberNo,Integer memberNo){
        return followDao.delete(followMemberNo,memberNo);
    }


    public Boolean checkFollow(Integer followMemberNo,Integer memberNo){
        return  followDao.existsByMemberNoAndFollowMemberNo(followMemberNo,memberNo);
    }
}
