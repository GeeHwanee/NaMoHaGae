package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class FollowRestController {
    @Autowired
    private FollowService service;


    @PatchMapping("/member/follow")
    public ResponseEntity<?> follow(Integer memberNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer myMemberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.follow(memberNo,myMemberNo));
    }

    @GetMapping("/follow/check")
    public  ResponseEntity<Boolean> checkFollow(Integer memberNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer myMemberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.checkFollow(memberNo,myMemberNo));
    }

    @GetMapping(value="/follow/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.list(pageno,memberNo));
    }
}
