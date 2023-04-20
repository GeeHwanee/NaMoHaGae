package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberDaoTest {
    @Autowired
    private MemberDao dao;
    @Test
    public void test1(){
        Member member = Member.builder().memberNo(1).townNo(0).memberEmail("qweasd@never.com").memberPhone("010-0101-0101").memberPassword("qweasdzxc").memberNickname("홍길동").memberIntroduce("저는 홍길동 입니다").build();
        dao.save(member);
    }
}
