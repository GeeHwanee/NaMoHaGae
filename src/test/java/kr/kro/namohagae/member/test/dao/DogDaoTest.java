package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.entity.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DogDaoTest {
    @Autowired
    private DogDao dao;

    @Test
    public void test1(){
        Dog dog = Dog.builder().dogNo(1).memberNo(1).dogProfile("default.jpg").dogCategory("골든 리트리버").dogGender(false).dogIntroduce("우리개는 안물어요").dogName("릴딘").
                dogBirthdayDate(LocalDateTime.now()).dogNotGenderEnabled(false).dogWeight(30.6).build();
        dao.register(dog);
    }

   //@Test
    public void test2(){

    }
}
