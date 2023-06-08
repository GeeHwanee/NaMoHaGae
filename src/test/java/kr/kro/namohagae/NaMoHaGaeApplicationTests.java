package kr.kro.namohagae;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NaMoHaGaeApplicationTests {

    @Autowired
    private BoardDao boardDao;
    @Test
    public void previewTest() {
        List<BoardDto.Preview> list = boardDao.preview(1, "", "boardReadCount", 1, 10);
        list.forEach(item -> System.out.println(item.toString()));
        Integer result = boardDao.countPreview(1, "");
        System.out.println("개수"+result);

    }
}
