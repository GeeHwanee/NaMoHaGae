package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MemberRestController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/member/update")
    public ResponseEntity<Void> update(MultipartFile profile, String nickname,String password,Authentication auth,String phone,String townDong,String introduce,Double longitude,Double latitude) {
        Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
        // profile은 null이 될 수 있다 -> 서비스에서 null 체크 -> null이면 변경하지 않는다
        // email은 중복 여부를 확인해야 한다 -> 중복되지 않는 경우 업데이트
        // 사진 + 이메일 -> 이메일이 겹치면 실패 -> 409를 보낸다
			// 기존 내 이메일과 일치한다면 false 리턴
		if (!memberService.checkUpdateNickanme(memberNo, nickname)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
        Boolean result = memberService.update(profile,nickname,memberNo,password,phone,townDong,introduce,longitude,latitude);
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
        Boolean a = memberService.checkEmail(email);
        return  ResponseEntity.ok(a);
    }
    @GetMapping("/member/checkNickname")
    public ResponseEntity<Boolean> checkNickname(String nickname){
        Boolean a = memberService.checkEmail(nickname);
        return  ResponseEntity.ok(a);
    }
    @GetMapping("/member/findEmail")
    public ResponseEntity<String> findEmailByNicknameAndPhone(String nickname,String phone){
        return ResponseEntity.ok(memberService.findEmailByNicknameAndPhone(nickname,phone));
    }

}
