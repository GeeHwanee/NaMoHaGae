package kr.kro.namohagae;

import kr.kro.namohagae.board.dao.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaMoHaGaeApplicationTests {

    @Autowired
    private BoardDao boardDao;

}
