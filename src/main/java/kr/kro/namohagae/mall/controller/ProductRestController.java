package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mall/product")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/list/recommend")
    public ResponseEntity<List<ProductDto.MdRecommend>> mdRecommend(){
        return ResponseEntity.ok(productService.mdRecommend());
    }

}
