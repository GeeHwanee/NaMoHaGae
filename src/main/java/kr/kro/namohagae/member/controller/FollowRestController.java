package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FollowRestController {
    @Autowired
    private FollowService service;

    @PostMapping("/follow/save")
    public ResponseEntity<Void> save(Integer followMemberNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo=myUserDetails.getMemberNo();
        if(service.save(followMemberNo,memberNo)==true){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Boolean result =service.save(followMemberNo,memberNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @PostMapping("/follow/delete")
    public ResponseEntity<Void> delete(Integer followMemberNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo=myUserDetails.getMemberNo();
        if(service.delete(followMemberNo,memberNo)==true){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Boolean result =service.delete(followMemberNo,memberNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @GetMapping("/follow/check")
    public  ResponseEntity<Void> checkFollow(Integer followMemberNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo=myUserDetails.getMemberNo();
        if(service.checkFollow(followMemberNo,memberNo)==true){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Boolean result =service.checkFollow(followMemberNo,memberNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
}
