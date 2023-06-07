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
    private final TownService townService;
    private final BoardService boardService;
    private final BoardTownService boardTownService;
    private final KnowledgeService knowledgeService;
    private final BoardNoticeService boardNoticeService;
    private final CommentService commentService;

    private final MemberService memberService;
    private final DogService dogService;

    private final ReportService reportService;
    private final ProductService productService;
    private final QnaService qnaService;
    private final AddressService addressService;

    private final ChatService chatService;
    private final MemberDao memberDao;
    private final ReviewService puchingReviewService;

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

    // [회원 파트]--------------------------------------------------------------------
    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/join")
    public void join(){}

    @PreAuthorize("isAnonymous()")
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

    // [퍼칭 파트]--------------------------------------------------------------------

    @Secured("ROLE_DOG")
    @GetMapping("/puching/chatroom")
    public String chatroom(Principal principal, Model model,@RequestParam(defaultValue = "")String receiverEmail) {
        Integer myMemberNo=memberDao.findNoByUsername(principal.getName());
        model.addAttribute("list",chatService.findAllChatRoom(myMemberNo));
        model.addAttribute("mymemberNo",myMemberNo);
        model.addAttribute("startuser",receiverEmail);
        return "puching/chatRoom";
    }
    @Secured("ROLE_DOG")
    @GetMapping("/puching/locationview")
    public String locationview(@RequestParam("lat")Double lat,@RequestParam("lng") Double lng){

        return "puching/locationview";
    };
    @Secured("ROLE_DOG")
    @GetMapping("/puching/reviewwrite")
    public String reviewwrite(@RequestParam("receiverNo")Integer receiverNo,@RequestParam("puchingNo")Integer puchingNo,Principal principal,Model model){
        System.out.println(receiverNo);
        System.out.println(puchingNo);
        ReviewDto.Writeview dto=puchingReviewService.findWriteViewInfo(principal.getName(),receiverNo,puchingNo);
        model.addAttribute("list",dto);
        System.out.println(dto);
        return "puching/reviewwrite";
    }
    @Secured("ROLE_DOG")
    @PostMapping(value="/puching/reviewwrite")
    public String write(ReviewDto.Write dto, Principal principal) {
        System.out.println(dto);
        puchingReviewService.saveReview(principal.getName(),dto);
        return "redirect:/puching/main";
    }
    @Secured("ROLE_DOG")
    @GetMapping(value = "/puching/puching_introduce")
    public String introduce(){
        return "puching/puching_introduce";
    }

    @Secured("ROLE_DOG")
    @GetMapping(value = "/puching/search")
    public String search(){
        return "puching/search";
    }



    // [게시판 파트]--------------------------------------------------------------------


    @GetMapping("/board/notice/read")
    public String noticeRead(Model model,Integer boardNoticeNo, @AuthenticationPrincipal MyUserDetails myUserDetails){
        Boolean result = boardService.isLikeExists(boardNoticeNo, myUserDetails.getMemberNo());
        if(!result){
            boardService.insertLike(boardNoticeNo, myUserDetails.getMemberNo());
        }
        model.addAttribute("read",boardNoticeService.findByBoardNoticeNo(boardNoticeNo));

        return "board/notice/read";
    }

    @GetMapping("/board/notice/list")
    public String noticeList(Model model) {

        model.addAttribute("preview", boardNoticeService.preview());

        return "board/notice/list";
    }

    @GetMapping("/board/town/write")
    public String boardTownWrite(Model model) {
        model.addAttribute("town",boardTownService.townList());
        return "board/town/write";
    }
    @PostMapping("/board/town/writepro")
    public String boardTownWritePro(BoardTownDto.write boardTownDto, Principal principal){



        boardTownService.boardTownInsertData(boardTownDto,principal.getName());

        return "redirect:/board/town/list";
    }
    @GetMapping("/board/town/read")
    public String boardTownRead(Model model, Integer boardNo,Principal principal) {



        boolean isLiked = boardService.isLikeExists(boardNo,memberDao.findNoByUsername(principal.getName()));
        if(isLiked){


        }
        else {
            boardService.insertLike(boardNo, memberDao.findNoByUsername(principal.getName()));
            boardService.readCnt(boardNo);

        }
        if(boardService.findLike(boardNo,memberDao.findNoByUsername(principal.getName())) == 1) {
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
    public String townList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails,Principal principal){

        model.addAttribute("town",townService.findFuck(memberDao.findNoByUsername(principal.getName())));
        model.addAttribute("logintownNo",myUserDetails.getTownNo());

        return "board/town/list";
    }

    @GetMapping("/board/knowledge/main")
    public String knowledgemain(Model model){
            model.addAttribute("readList",knowledgeService.readList());
            model.addAttribute("readList2",knowledgeService.readList2());
        return "board/knowledge/main";
    }

    @GetMapping("/board/knowledge/list")
    public String knowledgeList(@RequestParam(defaultValue="1")Integer pageNo, Model model){
       model.addAttribute("list",knowledgeService.questionFindAll(pageNo));
        return "board/knowledge/list";
    }

    @GetMapping("/board/knowledge/write")
    public String knowledgeWrite(){
        return "board/knowledge/write";
    }

    @PostMapping("/board/knowledge/write")
    public String knowledgeWrite(KnowledgeQuestionDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails, RedirectAttributes ra){
       Integer result = knowledgeService.questionSave(dto, myUserDetails.getMemberNo());
       if(result>0){
        return "redirect:/board/knowledge/read?knowledgeQuestionNo="+result;
        } else {
            ra.addFlashAttribute("msg","포인트가 부족합니다");
           return "redirect:/board/knowledge/write";
        }
       }

    @GetMapping("/board/knowledge/read")
    public String knowledgeRead(Integer knowledgeQuestionNo, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        boolean isLiked = boardService.isLikeExists(knowledgeQuestionNo,myUserDetails.getMemberNo());
        if(isLiked){
        }
        else {
            boardService.insertLike(knowledgeQuestionNo,myUserDetails.getMemberNo());
            knowledgeService.update();

        }
        model.addAttribute("question", knowledgeService.questionRead(knowledgeQuestionNo));
        model.addAttribute("memberNo", myUserDetails.getMemberNo());

        return "board/knowledge/read";
    }

    @PostMapping("board/knowledge/question/delete")
    public String knowledgeQuestionDelelte(Integer knowledgeQuestionNo){
        Integer result = knowledgeService.questionDelete(knowledgeQuestionNo);
        return "redirect:/board/knowledge/list";
    }

}
