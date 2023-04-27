package kr.kro.namohagae.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/member/join")
    public String join(MemberDto.Join dto){
        memberService.join(dto);
        return "redirect:/login";
    }

    @GetMapping("/member/my_profile")
    public ModelAndView read(Principal principal, Authentication auth){
      Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
      MemberDto.Read dto = memberService.read(memberNo);
      return new ModelAndView("member/my_profile").addObject("member",dto);
    }


    @PostMapping("/member/resign")
    public String resign(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication auth, RedirectAttributes ra) {
        Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
        memberService.resign(memberNo);
        handler.logout(req, res, auth);
        ra.addFlashAttribute("msg", "감사합니다. 꼭 다시 뵙겠습니다."); // 이런 메시지도 상수로 빼면 좋다
        return "redirect:/";
    }

    @PostMapping("/member/update")
    public ResponseEntity<Void> update(MultipartFile profile, String nickname,String password,Authentication auth,String phone,Integer townNo) {
        Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
        // profile은 null이 될 수 있다 -> 서비스에서 null 체크 -> null이면 변경하지 않는다
        // email은 중복 여부를 확인해야 한다 -> 중복되지 않는 경우 업데이트
        // 사진 + 이메일 -> 이메일이 겹치면 실패 -> 409를 보낸다

			// 기존 내 이메일과 일치한다면 false 리턴
		if (memberService.checkNickanme(memberNo,nickname)==false) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
        Boolean result = memberService.update(profile,nickname,memberNo,password,phone,townNo);
        return result? ResponseEntity.ok(null):ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }


}
