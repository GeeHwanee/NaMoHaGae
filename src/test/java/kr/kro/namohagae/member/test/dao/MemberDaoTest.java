package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberDaoTest {
    @Autowired
    private MemberDao dao;
 @Test
    public void test1(){

            Member member = Member.builder().memberNo(15).townNo(3).memberEmail("sj_hp@naver.com").memberPhone(" 010-0101-0101").memberPassword("1234").memberNickname(15+"번째 회원").build();
            dao.save(member);


}

//    @Test
    public void test2(){
    Member member = dao.findByMember(0).get();

//    dao.disabled(member.getMemberNo(),true);
    }
}
