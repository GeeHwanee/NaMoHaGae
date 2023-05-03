package kr.kro.namohagae.admin.controller;

import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AdminRestController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/product/put")
    public ResponseEntity<ProductDto.Read> productPut(ProductDto.Put dto) {
        Integer result = productService.put(dto);


        return ResponseEntity.ok(null);
    }
}
