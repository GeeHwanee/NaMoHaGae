package kr.kro.namohagae.member.test.dao;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static kr.kro.namohagae.mall.dto.ProductDto.Add;

@SpringBootTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    private Integer getPrice() {
        return (int)(Math.random()*10000)+5000;
    }

    //@Test
    public void insertProduct() {
        List<Integer> category = Arrays.asList(1,2,3,4);
        for(int i=1; i<=12; i++) {
            ProductDto.Add dto = new Add();
            // productNo 처리해야함...
            Product p = dto.toEntity(category.get(i%4),i+"번째 예제 상품",getPrice(),5,0,"예제 상품입니다", LocalDateTime.now();
            productDao.save(p);
        }
    }
}
