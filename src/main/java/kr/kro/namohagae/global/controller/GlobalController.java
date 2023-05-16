package kr.kro.namohagae.global.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.namohagae.board.dto.BoardTownDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.dto.NoticeDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.BoardList;
import kr.kro.namohagae.board.service.*;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.ReportService;
import kr.kro.namohagae.global.websocket.WebSocketService;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.QnaDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/*
    작성자: 박지환

*/

@RequiredArgsConstructor
@Controller
public class GlobalController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ReviewService puchingReviewService;
    @Autowired
    private QnaService qnaService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DogService dogService;
    @Autowired
    private BoardNoticeService boardNoticeService;

    private final KnowledgeService knowledgeService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardTownService boardTownService;
    @Autowired
    private CommentService commentService;

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

    @GetMapping("/member/report")
    public String report(Integer memberNo, Model model){
        model.addAttribute("memberNo", memberNo);
        return "/member/report";
    }

    @GetMapping("/login")
    public void login(){}

    // [회원 파트]--------------------------------------------------------------------
    @GetMapping("/member/join")
    public void join(){}

    @PostMapping("/member/join")
    public String join(@Valid MemberDto.Join dto, BindingResult br, RedirectAttributes ra,Model model){
        if(br.hasErrors()) {
            // ra의 값은 이동후 뷰페이지까지 유지된다. 그 다음에 제거
            String msg = br.getAllErrors().get(0).getDefaultMessage();
            ra.addFlashAttribute("msg", msg);
            return "redirect:/member/join";
        }
            model.addAttribute("dto",dto);
        return "member/addJoin";
    }

    @PostMapping("/member/kakaoJoin")
    public String kakao(String kakaoEmail,String kakaoName,Model model){
        model.addAttribute("dto",memberService.kakaoJoin(kakaoEmail,kakaoName));
        System.out.println("여기인가?");
        return "member/addJoin";
    }

    @PostMapping("/member/addJoin")
    public String addJoin(MemberDto.Join dto){
        System.out.println("아니면 여기?");
        memberService.join(dto);
        System.out.println(dto.getMemberEmail());
        System.out.println("");
        return "redirect:/";
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

    @GetMapping("/member/notification")
    public void notification(){}

    // [회원 파트]------[회원]--------------------------------------------------------
    @GetMapping("/member/information")
    public ModelAndView information(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        Integer memberNo = myUserDetails.getMemberNo();
        MemberDto.Read dto = memberService.read(memberNo);
        return new ModelAndView("/member/information").addObject("member",dto);

    }

    @GetMapping("/member/profile")
    public ModelAndView profile(Integer memberNo, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        if(memberNo.equals(myUserDetails.getMemberNo())){
            MemberDto.Read dto = memberService.read(myUserDetails.getMemberNo());
            return new ModelAndView("/member/information").addObject("member",dto);
        }
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
        return "/member/mall/addressCreate";
    }


    @GetMapping("/member/mall/qna")
    public void qnaList(){

    }



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
    @GetMapping("/puching/locationview")
    public String locationview(@RequestParam("lat")Double lat,@RequestParam("lng") Double lng){

        return "/puching/locationview.html";
    };

    @GetMapping("/puching/reviewwrite")
    public void reviewwrite(@RequestParam("receiverNo")Integer receiverNo,@RequestParam("puchingNo")Integer puchingNo,Principal principal,Model model){
        System.out.println(receiverNo);
        System.out.println(puchingNo);
        ReviewDto.writeview dto=puchingReviewService.findWriteViewInfo(principal.getName(),receiverNo,puchingNo);
        model.addAttribute("list",dto);

    }
    @PostMapping(value="/puching/reviewwrite")
    public void write(ReviewDto.write dto, Principal principal) {
        System.out.println(dto);
        puchingReviewService.saveReview(principal.getName(),dto);
    }

    // [게시판 파트]--------------------------------------------------------------------

    @GetMapping("/board/free/list")
    public String paging(Model model,
                         @RequestParam(value ="page", required = false, defaultValue = "1") int page,
                         @RequestParam(value ="searchName", defaultValue = "") String searchName) {

        List<BoardList> pagingList = boardService.pagingList(searchName,page);
        PageDto pageDTO = boardService.pagingParam(page);
        if (searchName != null && !searchName.isEmpty()) {
            model.addAttribute("searchName", searchName);
        }
        model.addAttribute("list", pagingList);
        model.addAttribute("paging", pageDTO);
        return "board/free/list";
    }

    @GetMapping("/board/notice/list")
    public String noticeList(){
        return "board/notice/list";
    }

    @GetMapping("/board/town/write")
    public String boardTownWrite() {

        return "board/town/write";
    }
    @PostMapping("/board/town/writepro")
    public String boardTownWritePro(BoardTownDto.write boardTownDto, Principal principal){


        boardTownService.boardTownInsertData(boardTownDto,principal.getName());

        return "redirect:/board/town/list";
    }
    @GetMapping("/board/town/read")
    public String boardTownRead(Model model, Integer boardNo,Principal principal) {

        boardTownService.townReadCnt(boardNo);

        boolean isLiked;
        isLiked = boardService.isLikeExists(boardNo,memberDao.findNoByUsername(principal.getName()));

        if(isLiked){
            model.addAttribute("good","좋아요취소");
        } else {
            model.addAttribute("good","좋아요");
        }
        model.addAttribute("modify",memberDao.findNoByUsername(principal.getName()));
        model.addAttribute("comment", commentService.commentList(boardNo));
        model.addAttribute("board",boardTownService.boardTownRead(boardNo));


        return "board/town/read";
    }
    @GetMapping("/board/town/delete")
    public String boardTownDelete(Integer boardNo) {
        boardTownService.townDeleteData(boardNo);

        return "redirect:/board/town/list";
    }

    @GetMapping("/board/town/list")
    public String townList(String townGu){


        return "/board/town/list";
    }

    @GetMapping("/board/knowledge/list")
    public String knowledgeList(@RequestParam(defaultValue="1")Integer pageNo, Model model){
       model.addAttribute("list",knowledgeService.questionFindAll(pageNo));
        return "/board/knowledge/list";
    }

    @GetMapping("/board/knowledge/write")
    public String knowledgeWrite(){
        return "/board/knowledge/write";
    }

    @PostMapping("/board/knowledge/write")
    public String knowledgeWrite(KnowledgeQuestionDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails){
       Integer result = knowledgeService.questionSave(dto, myUserDetails.getMemberNo());
        return "redirect:/board/knowledge/read?knowledgeQuestionNo="+result;
    }

    @GetMapping("/board/knowledge/read")
    public String knowledgeRead(Integer knowledgeQuestionNo, Model model){
        model.addAttribute("question", knowledgeService.questionRead(knowledgeQuestionNo));
        return "/board/knowledge/read";
    }

    // [쇼핑몰 파트]--------------------------------------------------------------------
    @GetMapping("/mall/cart")
    public void cart(){
    }

    // [관리자 파트]--------------------------------------------------------------------
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/list")
    public String adminNoticeList(Model model){
        model.addAttribute("list", boardNoticeService.list());
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
        boardNoticeService.addNotice(dto);

        return "redirect:/admin/notice/list";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/read")
    public String adminNoticeRead(Model model,Integer boardNoticeNo) {

        boardNoticeService.increaseReadCnt(boardNoticeNo);
        model.addAttribute("read", boardNoticeService.read(boardNoticeNo));
        return "admin/notice/read";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/delete")
    public String adminNoticeDelete(Integer boardNoticeNo){

        boardNoticeService.delete(boardNoticeNo);
        return "redirect:/admin/notice/list";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/modify")
    public String adminNoticeModify(Model model,Integer boardNoticeNo){

        model.addAttribute("read",boardNoticeService.read(boardNoticeNo));

        return "admin/notice/modify";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/notice/update")
    public String adminNoticeUpdate(Model model,NoticeDto.Update noticeDto) {
        System.out.println(noticeDto);
        boardNoticeService.update(noticeDto);
        return "redirect:/admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/list")
    public String adminProductList(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        model.addAttribute("list",productService.list(pageNo, null, myUserDetails.getMemberNo()));
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
