package kr.kro.namohagae;

import kr.kro.namohagae.global.dao.NotificationDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaMoHaGaeApplicationTests {
    @Autowired
    private NotificationDao dao;
    @Test
    void contextLoads() {
    }


    //@Test
   // public void test1(){
    //   dao.findAll(1,5).forEach(a->System.out.println(a));
   // }

//    @Test
//    public void test2(){
//        System.out.println(dao.read(1).toString());
//
//    }
}
