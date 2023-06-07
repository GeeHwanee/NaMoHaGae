package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.BoardTownDto;
import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.service.*;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@Controller
@RequiredArgsConstructor
public class BoardAllController {
    private final BoardService boardService;
    private final BoardTownService boardTownService;
    private final BoardNoticeService boardNoticeService;
    private final MemberDao memberDao;
    private final TownService townService;
    private final CommentService commentService;
    private final KnowledgeService knowledgeService;
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
