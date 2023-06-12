package kr.kro.namohagae.global.controller;


import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    작성자: 박지환

*/

@RequiredArgsConstructor
@Controller
public class GlobalController {
    private final BoardService boardService;
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
       model.addAttribute("ReadList",boardService.mainPreview(0,"boardReadCount"));
        model.addAttribute("RecommendList",boardService.mainPreview(0,"boardRecommendCount"));
        model.addAttribute("TownReadList",boardService.mainPreview(null,"boardReadCount"));
        model.addAttribute("TownRecommendList",boardService.mainPreview(null,"boardRecommendCount"));
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
