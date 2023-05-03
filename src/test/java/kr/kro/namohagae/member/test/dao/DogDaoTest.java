package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DogDaoTest {
    @Autowired
    private DogDao dao;



}
