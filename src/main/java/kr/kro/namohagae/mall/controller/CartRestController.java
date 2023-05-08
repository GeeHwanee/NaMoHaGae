package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.CartDetailDto;
import kr.kro.namohagae.mall.exception.ProductNotFoundException;
import kr.kro.namohagae.mall.exception.ProductStockException;
import kr.kro.namohagae.mall.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CartRestController {
    private final CartService service;

    // 장바구니 담기
    @PostMapping("/cart/add")
    public ResponseEntity<String> add(Integer productNo, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.add(productNo, memberNo));
    }

    // 장바구니 물품 개수 증가
    @PatchMapping("/cart/increase")
    public ResponseEntity<CartDetailDto.Update> increase(@RequestParam Integer productNo, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo=myUserDetails.getMemberNo();
        System.out.println(memberNo+"멤버보여요");
        return ResponseEntity.ok(service.increase(productNo, memberNo));
    }

    // 장바구니 물품 개수 감소
    @PatchMapping("/cart/decrease")
    public ResponseEntity<CartDetailDto.Update> decrease(@RequestParam Integer productNo, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.decrease(productNo, memberNo));
    }

    // 장바구니에서 작업하는 상품을 찾지 못했을 때 발생하는 예외
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("제품을 찾을 수 없습니다");
    }

    // 재고 문제(재고가 모자람, 사용자가 상품 개수를 0개로 지정)에서 발생하는 예외
    @ExceptionHandler(ProductStockException.class)
    public ResponseEntity<String> productStockException(ProductStockException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
