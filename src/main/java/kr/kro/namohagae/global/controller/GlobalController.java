package kr.kro.namohagae.global.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.namohagae.board.dto.BoardTownDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.service.*;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.ReportService;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.mall.service.AddressService;
import kr.kro.namohagae.mall.service.ProductService;
import kr.kro.namohagae.mall.service.QnaService;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.DogService;
import kr.kro.namohagae.member.service.MemberService;
import kr.kro.namohagae.puching.dto.ReviewDto;
import kr.kro.namohagae.puching.service.ChatService;
import kr.kro.namohagae.puching.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/*
    작성자: 박지환

*/

@RequiredArgsConstructor
@Controller
public class GlobalController {
    private final BoardService boardService;
    private final BoardTownService boardTownService;
    private final ProductService productService;

    // [Global 파트]--------------------------------------------------------------------

    @GetMapping(value = {"/", "/main"})
    public String main(Model model, HttpSession session,@AuthenticationPrincipal MyUserDetails myUserDetails){
      if(myUserDetails!=null) {
          String username = myUserDetails.getUsername();
          if (username.equals("admin")) {
              return "redirect:/admin/main";
          }
      }
        if (session.getAttribute("errorMessage") != null) {
            String errorMessage = (String) session.getAttribute("errorMessage");
            session.removeAttribute("errorMessage");
            model.addAttribute("message", errorMessage);
        }
        return "main";
    }
    @Secured("ROLE_DOG")
    @GetMapping("/puching/main")
    public String puchingMain() {
        return "puching/main";
    }

    @GetMapping("/mall/main")
    public String mallMain(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        model.addAttribute("newProduct", productService.newProductForMain(myUserDetails.getMemberNo()));
        model.addAttribute("bestProduct", productService.bestProductForMain(myUserDetails.getMemberNo()));
        return "mall/main";
    }

    @GetMapping("/board/main")
    public String boardMain(Model model){


        model.addAttribute("ReadList",boardService.mainReadList());
        model.addAttribute("RecommendList",boardService.mainRecommendList());
        model.addAttribute("TownReadList",boardTownService.mainReadList());
        model.addAttribute("TownRecommendList",boardTownService.mainRecommendList());
        return "board/main";
    }

    @GetMapping("/member/main")
    public String memberMain(){ return "member/main";}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/main")
    public String adminMain(){return "admin/main";}

    @GetMapping("/member/report")
    public String report(Integer memberNo, Model model){
        model.addAttribute("memberNo", memberNo);
        return "member/report";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public void login(HttpSession session, Model model){
        if (session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
    }

}
