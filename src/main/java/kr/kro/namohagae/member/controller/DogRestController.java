package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class DogRestController {
    @Autowired
    private DogService dogService;
    @PostMapping("/dog/update")
    public ResponseEntity<Void> update(MultipartFile profile, String name, String introduce,String notGenderEnabled,String weight,@AuthenticationPrincipal MyUserDetails myUserDetails, Integer dogNo) {
        System.out.println("12313");
        Integer memberNo= myUserDetails.getMemberNo();
        Boolean result = dogService.update(memberNo,profile,name,introduce,notGenderEnabled,weight,dogNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @GetMapping(value="/dog/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dogList(@AuthenticationPrincipal MyUserDetails myUserDetails,Integer profileMemberNo){
        Integer memberNo = 0;
        if (profileMemberNo!=null){
            memberNo=profileMemberNo;
        }else{
            memberNo=myUserDetails.getMemberNo();
        }
        return ResponseEntity.ok(dogService.dogList(memberNo));
    }
}
