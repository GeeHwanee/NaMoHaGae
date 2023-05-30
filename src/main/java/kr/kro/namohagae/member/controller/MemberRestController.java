package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MemberRestController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/member/update")
    public ResponseEntity<Void> update(@AuthenticationPrincipal MyUserDetails myUserDetails, MemberDto.UpdateMember dto) {
        Integer memberNo = myUserDetails.getMemberNo();
        // profile은 null이 될 수 있다 -> 서비스에서 null 체크 -> null이면 변경하지 않는다
        // email은 중복 여부를 확인해야 한다 -> 중복되지 않는 경우 업데이트
        // 사진 + 이메일 -> 이메일이 겹치면 실패 -> 409를 보낸다
        // 기존 내 이메일과 일치한다면 false 리턴
        Boolean result = memberService.update(dto,memberNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @PostMapping("/member/change/password")
    public ResponseEntity<Void> changePassword(String memberPassword,String memberEmail){
        Boolean result = memberService.changePassword(memberPassword,memberEmail);
        return result?ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @PatchMapping("/member/sendAudenticationCode")
    public ResponseEntity<String> sendmail(String email){
      Boolean a = memberService.sendAuthenticationCode(email);
        return a?ResponseEntity.ok("이메일로 인증코드를 보냈습니다"):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @GetMapping("/member/checkAudenticationCode")
    public ResponseEntity<String> checkCode(String code){
        Boolean a=memberService.checkAuthenticationCode(code);
        return a?ResponseEntity.ok("인증되었습니다"):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @GetMapping("/member/checkEmail")
    public ResponseEntity<Boolean> checkEmail(String email){
        System.out.println("asdasd");
        Boolean a = memberService.checkEmail(email);
        System.out.println(a);
        return  ResponseEntity.ok(a);
    }
    @GetMapping("/member/checkNickname")
    public ResponseEntity<Boolean> checkNickname(String nickname){
        Boolean a = memberService.checkNickname(nickname);
        return  ResponseEntity.ok(a);
    }
    @GetMapping("/member/findEmail")
    public ResponseEntity<String> findEmailByNicknameAndPhone(String nickname,String phone){
        return ResponseEntity.ok(memberService.findEmailByNicknameAndPhone(nickname,phone));
    }

    @GetMapping("/member/point")
    public ResponseEntity<Integer> findPointByMemberNo(@AuthenticationPrincipal MyUserDetails myUserDetails){
        return ResponseEntity.ok(memberService.findMemberPointByMemberNo(myUserDetails.getMemberNo()));
    }
}
