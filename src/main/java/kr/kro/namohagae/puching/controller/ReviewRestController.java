package kr.kro.namohagae.puching.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.FollowService;
import kr.kro.namohagae.puching.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FollowService followService;
    @GetMapping(value="/review/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,Integer memberNo) {
        return ResponseEntity.ok(reviewService.findContentByReceiverNo(pageno,memberNo));
    }
    @GetMapping(value="/api/v1/review/imfo", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo = myUserDetails.getMemberNo();
        return ResponseEntity.ok(reviewService.findContentByWriterNo(pageno,memberNo));
    }


}
