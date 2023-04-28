package kr.kro.namohagae.mall.test.dao;

import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.ProductImageDao;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductImage;
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
    //@Test
    public void insertProduct() {
        List<Integer> category = Arrays.asList(1,2,3,4);
        for(int i=1; i<=40; i++) {
            ProductDto.Add dto = new Add(null,category.get(i%4),i+"번째 예제 상품",getPrice(),5,0,"예제 상품입니다", LocalDateTime.now(),null);
            Product p = dto.toEntity();
            productDao.save(p);
            List<ProductImage> images = new ArrayList<>();
            images.add(new ProductImage(p.getProductNo(), 1, "image1.png"));
            images.add(new ProductImage(p.getProductNo(), 2, "image2.png"));
            images.add(new ProductImage(p.getProductNo(), 3, "image3.png"));
            productImageDao.save(images);
        }
    }

    // 특정 카테고리의 상품 개수를 조회
    //@Test
    public void countOfCategory() {
        Integer categoryNo = 1;
        Integer result = productDao.count(categoryNo);
        System.out.println(categoryNo + "번 카테고리 상품 개수: " + result);
    }

    // 상품명으로 검색
    //@Test
    public void findByProductNameTest() {
        List<Product> products = productDao.findByProductName("%2%");
        int count = products.size();
        System.out.println("2가 포함된 상품명 개수: " + count);
    }


}
