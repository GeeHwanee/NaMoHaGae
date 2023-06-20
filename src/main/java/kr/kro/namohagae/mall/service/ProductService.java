package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.mall.dao.ProductDao;
import kr.kro.namohagae.mall.dao.ProductImageDao;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.entity.Product;
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
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;

    private final Integer PAGESIZE = 9;
    private final Integer BLOCKSIZE = 5;

    @Transactional
    public Integer add(ProductDto.Add dto) {
        Integer imageIndex = 1;
        String originalFilename = "default.jpg";
        Product product = dto.toEntity();
        productDao.save(product);

        List<ProductImage> images = new ArrayList<>();
        for(MultipartFile image: dto.getProductImages()) {
            if(image!=null && !image.isEmpty()) {
                originalFilename = image.getOriginalFilename();
                File saveFile = new File(ImageConstants.IMAGE_PRODUCT_DIRECTORY, originalFilename);
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
        String originalFilename = "default.jpg";
        Product product = dto.toEntity();
        productDao.update(product);
        if (dto.getProductImages()!=null) {
            List<ProductImage> images = new ArrayList<>();
            for (MultipartFile image : dto.getProductImages()) {
                if (image != null && !image.isEmpty()) {
                    originalFilename = image.getOriginalFilename();
                    File saveFile = new File(ImageConstants.IMAGE_PRODUCT_DIRECTORY, originalFilename);
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

    // 필터 정렬
    public ProductDto.Pagination findAll(Integer pageNo, Integer categoryNo, String sortBy, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products = null;

        if (sortBy.equals("NewProduct")) {
            products =  productDao.findAllByNewProduct(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, categoryNo, memberNo);
        } else if (sortBy.equals("ProductName")) {
            products = productDao.findAllByProductName(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, categoryNo, memberNo);
        } else if (sortBy.equals("BestProduct")) {
            products = productDao.findAllByBestProduct(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, categoryNo, memberNo);
        }

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

    public ProductDto.Pagination list(Integer pageNo, Integer categoryNo, Integer memberNo) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products =  productDao.findAll(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, categoryNo, memberNo);
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
        ProductDto.Read dto = productDao.findByProductNo(ImageConstants.IMAGE_PRODUCT_URL, productNo);
        return dto;
    }

    @Transactional
    public Boolean delete(Integer productNo) {
        Integer imageDeleteResult = productImageDao.delete(productNo);
        Integer productDeleteResult = productDao.delete(productNo);
        return (imageDeleteResult>0)&&(productDeleteResult>0);
    }

    public ProductDto.Pagination searchProductName(Integer pageNo, Integer categoryNo, Integer memberNo, String productName) {
        Integer startRowNum = (pageNo-1)*PAGESIZE + 1;
        Integer endRowNum = startRowNum + PAGESIZE - 1;
        List<ProductDto.ReadAll> products = productDao.findByProductName(ImageConstants.IMAGE_PRODUCT_URL,startRowNum, endRowNum, categoryNo, memberNo, productName);
        Integer countOfProduct = productDao.countByProductName(categoryNo, productName);
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

    public List<ProductDto.ReadAll> newProductForMain(Integer memberNo) {
        List<ProductDto.ReadAll> newProduct = productDao.findAllByNewProductForMain(ImageConstants.IMAGE_PRODUCT_URL,memberNo);

        List<ProductDto.ReadAll> products = new ArrayList<>();
        for (int i = 0; i < Math.min(newProduct.size(), 5); i++) {
            products.add(newProduct.get(i));
        }

        return products;
    }
    public List<ProductDto.ReadAll> bestProductForMain(Integer memberNo) {
        List<ProductDto.ReadAll> bestProduct = productDao.findAllByBestProductForMain(ImageConstants.IMAGE_PRODUCT_URL,memberNo);

        List<ProductDto.ReadAll> products = new ArrayList<>();
        for (int i = 0; i < Math.min(bestProduct.size(), 5); i++) {
            products.add(bestProduct.get(i));
        }

        return products;
    }

    public List<ProductDto.MdRecommend> mdRecommend() {
        return productDao.findByRecommendEnabled(ImageConstants.IMAGE_PRODUCT_URL);
    }
}