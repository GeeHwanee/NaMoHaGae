package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class MemberDaoTest {
    @Autowired
    private MemberDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
 @Test
    public void test1(){

            Member member = Member.builder().memberNo(0).townNo(3).memberEmail("rlrlrl").memberPhone("010-0101-0101").memberPassword(passwordEncoder.encode("1234")).memberNickname(15+"번째 회원").memberIntroduce("안녕하세요").memberProfileImage("default.jpg").build();
            dao.save(member);


}

//    @Test
    public void test2(){
    Member member = dao.findByMember(0).get();

//    dao.disabled(member.getMemberNo(),true);
    }
}
