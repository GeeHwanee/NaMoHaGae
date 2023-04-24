package kr.kro.namohagae.mall.test.dao;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.ProductImageDao;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kr.kro.namohagae.mall.dto.ProductDto.Add;

@SpringBootTest
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImageDao productImageDao;


    private Integer getPrice() {
        return (int)(Math.random()*10000)+5000;
    }
    
    // 상품 더미 테스트
    @Test
    public void insertProduct() {
        List<Integer> category = Arrays.asList(1,2,3,4);
        for(int i=1; i<=20; i++) {
            ProductDto.Add dto = new Add(null,category.get(i%4),i+"번째 예제 상품",getPrice(),5,0,"예제 상품입니다", LocalDateTime.now(),null);
            Product p = dto.toEntity();
            productDao.save(p);
            List<ProductImage> images = new ArrayList<>();
            images.add(new ProductImage(1, p.getProductNo(), "image1.png"));
            images.add(new ProductImage(2, p.getProductNo(), "image2.png"));
            images.add(new ProductImage(3, p.getProductNo(), "image3.png"));
            productImageDao.save(images);
        }
    }
}
