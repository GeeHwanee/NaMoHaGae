package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.FollowService;
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
    private ReviewService reviewService;
    @Autowired
    private FollowService followService;
    @GetMapping(value="/api/v1/review/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,Integer memberNo) {
        System.out.println(memberNo);
        return ResponseEntity.ok(reviewService.findContentByReceiverNo(pageno,memberNo));
    }
    @GetMapping(value="/api/v1/review/imfo", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo = myUserDetails.getMemberNo();
        System.out.println(memberNo);
        return ResponseEntity.ok(reviewService.findContentByWriterNo(pageno,memberNo));
    }
}
