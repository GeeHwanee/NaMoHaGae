package kr.kro.namohagae.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
        System.out.println("11");
        System.out.println(principal.getName());
      Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
        System.out.println("11");
      MemberDto.Read dto = memberService.read(memberNo);
        System.out.println("11");
      return new ModelAndView("member/my_profile").addObject("member",dto);
    }


    @PostMapping("/member/resign")
    public String resign(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication auth, RedirectAttributes ra) {
        memberService.resign(auth.getName());
        handler.logout(req, res, auth);
        ra.addFlashAttribute("msg", "감사합니다. 꼭 다시 뵙겠습니다."); // 이런 메시지도 상수로 빼면 좋다
        return "redirect:/";
    }


}
