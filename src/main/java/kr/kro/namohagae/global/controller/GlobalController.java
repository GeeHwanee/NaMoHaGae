package kr.kro.namohagae.global.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.namohagae.board.dto.NoticeDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.service.ProductService;
import kr.kro.namohagae.mall.service.QnaService;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.DogService;
import kr.kro.namohagae.member.service.MemberService;
import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private QnaService qnaService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DogService dogService;


    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardTownService boardTownService;
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

        return "/board/main";
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

    @PostMapping("/member/join")
    public String join(MemberDto.Join dto){
        System.out.println(dto.getMemberLatitude());
        memberService.join(dto);
        return "redirect:/login";
    }

    @PostMapping("/dog/registeration")
    public String save(@AuthenticationPrincipal MyUserDetails myUserDetails, DogDto.registeration dto){
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
    public String dogResign(Integer dogNo,SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication auth, RedirectAttributes ra) {
        dogService.resign(dogNo);
        handler.logout(req, res, auth);
        ra.addFlashAttribute("msg", "삭제했습니다"); // 이런 메시지도 상수로 빼면 좋다
        return "redirect:/";
    }

    @GetMapping("/member/dog/registeration")
    public  void dogRegisteration(){}

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
    @GetMapping("/member/dog/profile")
    public  ModelAndView dogProfile(Integer dogNo){
        DogDto.Read dto = dogService.read(dogNo);
        return  new ModelAndView("/member/dog/profile").addObject("dog",dto);
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

    @GetMapping("board/town/write")
    public String boardTownWrite() {

        return "board/town/write";
    }
    @PostMapping("/board/town/writepro")
    public String boardTownWritePro(Board board){


        boardTownService.boardTownInsertData(board);
        return "redirect:/board/town/list";
    }
    @GetMapping("/board/town/read")
    public String boardTownRead(Model model, Integer boardNo) {

        boardTownService.townReadCnt(boardNo);
        model.addAttribute("board",boardTownService.boardTownRead(boardNo));


        return "board/town/read";
    }
    @GetMapping("/board/town/delete")
    public String boardTownDelete(Integer boardNo) {
        boardTownService.townDeleteData(boardNo);

        return "redirect:/board/town/list";
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
    public void cart(){
    }

    // [관리자 파트]--------------------------------------------------------------------
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/list")
    public String adminNoticeList(){
        return "admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/write")
    public String adminNoticeWrite(){
        return "admin/notice/write";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/notice/write")
    public String adminNoticeWrite(NoticeDto.Add dto){
        boardService.addNotice(dto);

        return "redirect:/admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/list")
    public String adminProductList(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model){
        model.addAttribute("list",productService.list(pageNo, null));
        return "admin/product/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/report/list")
    public String adminReportList(){ return "admin/report/list";}
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/write")
    public void adminProductWrite(){}

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/product/write")
    public String adminProductWrite(ProductDto.Add dto){
        productService.add(dto);
        return "redirect:/admin/product/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/read")
    public String read(Integer productNo, Model model) {
        model.addAttribute("product", productService.read(productNo));
        return "admin/product/read";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/product/delete")
    public String deleteProduct(Integer productNo){
       Boolean result =  productService.delete(productNo);
       if (result) {
           return "redirect:/admin/product/list";
       }else{
           return "redirect:/admin/main";
       }


    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/qna/list")
    public String adminQnaList(Model model){
        model.addAttribute("list", qnaService.readAll());
        return  "admin/qna/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/qna/write")
    public String adminQnaWrite(Integer qnaNo, Model model){
        model.addAttribute("qna",qnaService.print(qnaNo));
        return "admin/qna/write";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/qna/write")
    public String adminQnaWrite(QnaDto.Put dto){
        qnaService.update(dto);
        return "redirect:/admin/qna/list";
    }

    // -------------------------------------------------------------------------------

}
