package kr.kro.namohagae.mall.test.dao;

import kr.kro.namohagae.mall.dao.FavoriteDao;
import kr.kro.namohagae.mall.entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FavoriteDaoTest {
    @Autowired
    private FavoriteDao favoriteDao;

    // 찜리스트 저장 테스트
    //@Test
    public void saveTest() {
        for(int i=1; i<=10; i++) {
            Favorite favorite = Favorite.builder().favoriteNo(1).memberNo(1).productNo(i).build();
            favoriteDao.save(favorite);
        }
    }

    // 해당 멤버의 찜리스트 찾기
    //@Test
    public void findByMemberNoTest() {
        List<Favorite> count = favoriteDao.findByMemberNo(0);
        System.out.println("0번 회원의 찜 개수: " + count.size());
        System.out.println("0번 회원의 찜 목록: " + count.toString());
    }

    // 찜리스트 삭제
    //@Test
    public void deleteTest() {
        favoriteDao.delete(1, 1);
        List<Favorite> count = favoriteDao.findByMemberNo(1);
        System.out.println("1번 회원의 찜 개수: " + count.size());
    }
}
