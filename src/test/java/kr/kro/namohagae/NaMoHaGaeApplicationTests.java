package kr.kro.namohagae;

import kr.kro.namohagae.member.dao.AlarmDao;
import kr.kro.namohagae.member.entity.Alarm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaMoHaGaeApplicationTests {
    @Autowired
    private AlarmDao dao;
    @Test
    void contextLoads() {
    }

    @Test
    public void test3(){
        System.out.println(dao.read(0).toString());
      dao.alarmRead(0);
        System.out.println(dao.read(0).toString());
    }

    @Test
    public void test1(){
       dao.findAll(1,5).forEach(a->System.out.println(a));
    }

//    @Test
//    public void test2(){
//        System.out.println(dao.read(1).toString());
//
//    }
}
