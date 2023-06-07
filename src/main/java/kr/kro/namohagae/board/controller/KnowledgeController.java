package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.KnowledgeQuestionDto;
import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.KnowledgeService;
import kr.kro.namohagae.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class KnowledgeController {
    private final BoardService boardService;
    private final KnowledgeService knowledgeService;
    private final BoardInsightService boardInsightService;

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
        Boolean isRead = boardInsightService.existsByBoardNoAndMemberNo(knowledgeQuestionNo, myUserDetails.getMemberNo());
        if(!isRead){
            boardInsightService.save(knowledgeQuestionNo, myUserDetails.getMemberNo());
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
