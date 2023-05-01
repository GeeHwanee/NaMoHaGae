package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.puchingtest.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewRestController {
    @Autowired
    private ReviewService service;
    @GetMapping(value="/api/v1/review/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.findContent(pageno,memberNo));
    }
}
