package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/member/join")
    public String join(MemberDto.Join dto){
        memberService.join(dto);
        return "redirect:/login";
    }


}
