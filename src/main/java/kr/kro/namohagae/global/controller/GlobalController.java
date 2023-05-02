package kr.kro.namohagae.global.controller;


import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/*
    작성자: 박지환

*/

@Controller
public class GlobalController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BoardService boardService;
    // [Global 파트]--------------------------------------------------------------------
    @GetMapping(value = {"/", "/main"})
    public String main(@AuthenticationPrincipal MyUserDetails myUserDetails){
      if(myUserDetails!=null) {
          String username = myUserDetails.getUsername();
          if (username.equals("admin")) {
              return "/admin/main";
          }
      }
        return "main.html";
    }

    @GetMapping("/puching/main")
    public String puchingMain() {
        return "/puching/main.html";
    }

    @GetMapping("/mall/main")
    public String mallMain(){
        return "/mall/main.html";
    }

    @GetMapping("/board/main")
    public String boardMain(){
        return "/board/main.html";
    }

    @GetMapping("/member/main")
    public String memberMain(){ return "/member/main.html";}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/main")
    public String adminMain(){return "/admin/main";}

    @GetMapping("/login")
    public void login(){}

    // [회원 파트]--------------------------------------------------------------------
    @GetMapping("/member/join")
    public void join(){}

    @GetMapping("/member/find")
    public void find(){}

    @GetMapping("/member/alarm")
    public void alarm(){}

    // [회원 파트]------[회원]--------------------------------------------------------
    @GetMapping("/member/information")
    public ModelAndView information(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        Integer memberNo = myUserDetails.getMemberNo();
        MemberDto.Read dto = memberService.read(memberNo);
        return new ModelAndView("/member/information").addObject("member",dto);

    }

    @GetMapping("/member/profile")
    public ModelAndView profile(Integer memberNo, Model model){
        MemberDto.Read dto = memberService.read(memberNo);
        return new ModelAndView("/member/profile").addObject("member",dto);
    }


    // [회원 파트]------[퍼칭]--------------------------------------------------------
    @GetMapping("/member/puching/review")
    public String puchingReview(){
        return "/member/puching/review";
    }

    @GetMapping("/member/puching/follow")
    public void follow(){}

    // [회원 파트]------[쇼핑몰]------------------------------------------------------
    @GetMapping("/member/mall/favorite")
    public void favorite(){}

    @GetMapping("/member/mall/order")
    public void order(){}

    @GetMapping("/member/mall/address")
    public void address(){}

    // [회원 파트]------[게시판]------------------------------------------------------
    @GetMapping("/member/board/post")
    public void post(){}

    @GetMapping("/member/board/comment")
    public void comment(){}

    @GetMapping("/member/board/question")
    public void question(){}

    @GetMapping("/member/board/answer")
    public void answer(){}

    // [퍼칭 파트]--------------------------------------------------------------------
    @GetMapping("/puching/chatroom")
    public void chatroom(Principal principal, Model model,@RequestParam(defaultValue = "")String receiverEmail) {
        Integer myMemberNo=memberDao.findNoByUsername(principal.getName());
        model.addAttribute("list",chatService.findAllChatRoom(myMemberNo));
        model.addAttribute("mymemberNo",myMemberNo);
        model.addAttribute("startuser",receiverEmail);
    }

    // [게시판 파트]--------------------------------------------------------------------

    @GetMapping("/board/free/list")
    public String paging(Model model,
                         @RequestParam(value ="page", required = false, defaultValue = "1") int page) {

        List<Board> pagingList = boardService.pagingList(page);
        PageDto pageDTO = boardService.pagingParam(page);
        model.addAttribute("list", pagingList);
        model.addAttribute("paging", pageDTO);
        return "board/free/list";
    }

    @GetMapping("/board/notice/list")
    public String noticeList(){
        return "board/notice/list";
    }

    @GetMapping("/board/town/list")
    public String townList(){
        return "/board/town/list";
    }

    @GetMapping("/board/knowledge/list")
    public String knowledgeList(){
        return "/board/knowledge/list";
    }

    // [쇼핑몰 파트]--------------------------------------------------------------------
    @GetMapping("/mall/cart")
    public void cart(){}

    // [관리자 파트]--------------------------------------------------------------------
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/list")
    public String adminNoticeList(){
        return "admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/qna/list")
    public String adminQnaList(){ return  "admin/qna/list";}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/list")
    public String adminProductList(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model){
        model.addAttribute("list",productService.list(pageNo, null));
        return "admin/product/list";
    }
    @GetMapping("/admin/report/list")
    public String adminReportList(){ return "admin/report/list";}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/write")
    public void adminProductWrite(){}

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/product/write")
    public String adminProductWrite(ProductDto.Add dto){
        Integer productNo = productService.add(dto);
        System.out.println(dto.getProductImages().size());
        return "redirect:/admin/product/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/read")
    public String read(Integer productNo, Model model) {
        Integer no = productNo;
        System.out.println(no);
        model.addAttribute("product", productService.read(productNo));
        return "admin/product/read";
    }
    // -------------------------------------------------------------------------------

}
