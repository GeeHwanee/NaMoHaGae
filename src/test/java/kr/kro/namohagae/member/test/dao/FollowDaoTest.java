package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.FollowDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FollowDaoTest {
    @Autowired
    private FollowDao dao;

    @Test
    public void test1(){
        dao.save(0,81);
    }
}
