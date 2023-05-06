package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.mall.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class QnaRestController {
    private final QnaService service;

    @GetMapping(value="/mall/product/qna", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageNo, Integer productNo) {
        System.out.println(productNo);
        System.out.println(pageNo + "현재페이지");
        return ResponseEntity.ok(service.list(pageNo,productNo));
    }
}
