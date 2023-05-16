package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.mall.dao.*;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
import kr.kro.namohagae.mall.entity.ProductCategory;
import kr.kro.namohagae.mall.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductCategoryDao productCategoryDao;
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;
    private final ProductReviewDao productReviewDao;
    private final QnaDao qnaDao;

    private final Integer PAGESIZE = 9;
    private final Integer BLOCKSIZE = 5;

//    public List<ProductCategory> findAll() {
//        return productCategoryDao.findAll();
//    }

    @Transactional
    public Integer add(ProductDto.Add dto) {
        Integer imageIndex = 1;
        String currentDir = System.getProperty("user.dir")+"/";
        String originalFilename = "default.jpg";
        Product product = dto.toEntity();
        productDao.save(product);

        List<ProductImage> images = new ArrayList<>();
        for(MultipartFile image: dto.getProductImages()) {
            if(image!=null && !image.isEmpty()) {
                originalFilename = image.getOriginalFilename();
                File saveFile = new File(currentDir+ImageConstants.IMAGE_PRODUCT_FOLDER, originalFilename);
                try {
                    image.transferTo(saveFile);
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
                images.add(new ProductImage(product.getProductNo(), imageIndex++, originalFilename));
            }
        }
        if(images.size()==0)
            images.add(new ProductImage(product.getProductNo(), 1, originalFilename));
        productImageDao.save(images);
        return product.getProductNo();
    }

    @Transactional
    public Integer put(ProductDto.Put dto) {
        Integer imageIndex = 1;
        String currentDir = System.getProperty("user.dir")+"/";
        String originalFilename = "default.jpg";
        Product product = dto.toEntity();
        productDao.update(product);
        if (dto.getProductImages()!=null) {
            List<ProductImage> images = new ArrayList<>();
            for (MultipartFile image : dto.getProductImages()) {
                if (image != null && !image.isEmpty()) {
                    originalFilename = image.getOriginalFilename();
                    File saveFile = new File(currentDir + ImageConstants.IMAGE_PRODUCT_FOLDER, originalFilename);
                    try {
                        image.transferTo(saveFile);
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    images.add(new ProductImage(product.getProductNo(), imageIndex++, originalFilename));
                }
            }
            if (images.size() == 0)
                images.add(new ProductImage(product.getProductNo(), 1, originalFilename));

            productImageDao.update(images);
        }
        return product.getProductNo();
    }

    public ProductDto.Pagination list(Integer pageNo, Integer categoryNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products =  productDao.findAll(startRowNum, endRowNum, categoryNo, memberNo);
        Integer countOfProduct = productDao.count(categoryNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductDto.Pagination(pageNo, prev, start, end, next, categoryNo, products);
    }

    // 필터(최신순) 정렬
    public ProductDto.Pagination findAllByLatestOrder(Integer pageNo, Integer categoryNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products = productDao.findAllByLatestOrder(startRowNum, endRowNum, categoryNo, memberNo);
        Integer countOfProduct = productDao.count(categoryNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductDto.Pagination(pageNo, prev, start, end, next, categoryNo, products);
    }

    // 필터(판매량) 정렬
    public ProductDto.Pagination findAllByOrderOfHighSales(Integer pageNo, Integer categoryNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products = productDao.findAllByOrderOfHighSales(startRowNum, endRowNum, categoryNo, memberNo);
        Integer countOfProduct = productDao.count(categoryNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductDto.Pagination(pageNo, prev, start, end, next, categoryNo, products);
    }

    // 필터(이름순) 정렬
    public ProductDto.Pagination findAllByProductName(Integer pageNo, Integer categoryNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products = productDao.findAllByProductName(startRowNum, endRowNum, categoryNo, memberNo);
        Integer countOfProduct = productDao.count(categoryNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;
        Integer prev = (pageNo-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ProductDto.Pagination(pageNo, prev, start, end, next, categoryNo, products);
    }


    public ProductDto.Read read(Integer productNo) {
        ProductDto.Read dto = productDao.findByProductNo(productNo);
        return dto;
    }

    @Transactional
    public Boolean delete(Integer productNo) {
        Integer imageDeleteResult = productImageDao.delete(productNo);
        Integer productDeleteResult = productDao.delete(productNo);

        return (imageDeleteResult>0)&&(productDeleteResult>0);
    }

}