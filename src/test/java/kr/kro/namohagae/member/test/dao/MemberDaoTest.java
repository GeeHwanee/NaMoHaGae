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
        Member member = Member.builder().memberNo(2).townNo(0).memberEmail("qweasd@never.com").memberPhone("010-0101-0101").memberPassword("qweasdzxc").memberNickname("홍길동").build();
        dao.save(member);
    }

    @Test
    public void test2(){
    Member member = dao.findByMemberNo(0).get();

    dao.updateMember(member.getMemberNo(),"asdasd","etertert","sdgdfg","");
    }
}
