package kr.kro.namohagae.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.DogService;
import kr.kro.namohagae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final DogService dogService;
    // [회원 파트]--------------------------------------------------------------------
    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/join")
    public void join(){}

    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/join")
    public String join(@Valid MemberDto.Join dto, BindingResult br, RedirectAttributes ra, Model model){
        if(br.hasErrors()) {
            // ra의 값은 이동후 뷰페이지까지 유지된다. 그 다음에 제거
            String msg = br.getAllErrors().get(0).getDefaultMessage();
            ra.addFlashAttribute("msg", msg);
            return "redirect:/member/join";
        }
        model.addAttribute("dto",dto);
        return "member/addJoin";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/kakaoJoin")
    public String kakao(String kakaoEmail,String kakaoName,Model model){
        model.addAttribute("dto",memberService.kakaoJoin(kakaoEmail,kakaoName));
        return "member/addJoin";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/member/addJoin")
    public String addJoin(MemberDto.Join dto){
        memberService.join(dto);
        return "redirect:/";
    }


    @PostMapping("/dog/registeration")
    public String save(@AuthenticationPrincipal MyUserDetails myUserDetails, DogDto.Registeration dto){
        Integer memberNo = myUserDetails.getMemberNo();
        dogService.save(dto,memberNo);
        return "redirect:/member/main";
    }

    @PostMapping("/member/resign")
    public String memberResign(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication auth, RedirectAttributes ra) {
        Integer memberNo = ((MyUserDetails)auth.getPrincipal()).getMemberNo();
        memberService.resign(memberNo);
        handler.logout(req, res, auth);
        ra.addFlashAttribute("msg", "감사합니다. 꼭 다시 뵙겠습니다."); // 이런 메시지도 상수로 빼면 좋다
        return "redirect:/";
    }
    @PostMapping("/dog/resign")
    public String dogResign(Integer dogNo,SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication auth, RedirectAttributes ra,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        dogService.resign(dogNo,memberNo);
        ra.addFlashAttribute("msg", "삭제했습니다"); // 이런 메시지도 상수로 빼면 좋다
        return "redirect:/";
    }

    @GetMapping("/member/dog/registeration")
    public  void dogRegisteration(){}

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/find")
    public void find(){}

    @GetMapping("/member/notification")
    public void notification(){}

    // [회원 파트]------[회원]--------------------------------------------------------
    @GetMapping("/member/information")
    public ModelAndView information(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        Integer memberNo = myUserDetails.getMemberNo();
        MemberDto.Read dto = memberService.read(memberNo);
        return new ModelAndView("member/information").addObject("member",dto);

    }

    @GetMapping("/member/profile")
    public ModelAndView profile(Integer memberNo, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        if(memberNo.equals(myUserDetails.getMemberNo())){
            MemberDto.Read dto = memberService.read(myUserDetails.getMemberNo());
            return new ModelAndView("/member/information").addObject("member",dto);
        }
        MemberDto.Read dto = memberService.read(memberNo);
        return new ModelAndView("member/profile").addObject("member",dto);

    }
    @GetMapping("/member/dog/profile")
    public  ModelAndView dogProfile(Integer dogNo){
        DogDto.Read dto = dogService.read(dogNo);
        return  new ModelAndView("member/dog/profile").addObject("dog",dto);
    }


    // [회원 파트]------[퍼칭]--------------------------------------------------------
    @GetMapping("/member/puching/review")
    public String puchingReview(){
        return "member/puching/review";
    }

    @GetMapping("/member/follow")
    public void follow(){}


    // [회원 파트]------[쇼핑몰]------------------------------------------------------
    @GetMapping("/member/mall/favorite")
    public void favorite(){}

    @GetMapping("/member/mall/order")
    public void order(){}

    @GetMapping("/member/mall/address")
    public void address(){}
    @GetMapping("/kakaoLogin")
    public void kakaoLogin(){}

    @GetMapping("/member/mall/address/add")
    public String addressCreate(){
        return "member/mall/addressCreate";
    }


    @GetMapping("/member/mall/qna")
    public void qnaList(){

    }
    @GetMapping("/member/mall/review")
    public void memberReview(){}



    // [회원 파트]------[게시판]------------------------------------------------------
    @GetMapping("/member/board/post")
    public void post(){}

    @GetMapping("/member/board/comment")
    public void comment(){}

    @GetMapping("/member/board/question")
    public void question(){}

    @GetMapping("/member/board/answer")
    public void answer(){}
}
