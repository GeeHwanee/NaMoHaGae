package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.FollowService;
import kr.kro.namohagae.puchingtest.dto.ReviewDto;
import kr.kro.namohagae.puchingtest.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FollowService followService;
    @GetMapping(value="/review/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,Integer memberNo) {
        System.out.println(memberNo);
        return ResponseEntity.ok(reviewService.findContent(pageno,memberNo));
    }


}
