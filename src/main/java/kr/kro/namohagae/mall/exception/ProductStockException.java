package kr.kro.namohagae.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductStockException extends RuntimeException {
    private String message;
}
