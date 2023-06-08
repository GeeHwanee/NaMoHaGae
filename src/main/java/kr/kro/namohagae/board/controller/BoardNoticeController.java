package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardNoticeService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.global.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class BoardNoticeController {
    private final BoardService boardService;
    private final BoardNoticeService boardNoticeService;
    private final BoardInsightService boardInsightService;

    @GetMapping("/board/notice/read")
    public String noticeRead(Model model, Integer boardNoticeNo, @AuthenticationPrincipal MyUserDetails myUserDetails){
        Boolean isRead = boardInsightService.existsByBoardNoAndMemberNo(boardNoticeNo, myUserDetails.getMemberNo());
        if(!isRead){
            boardInsightService.save(boardNoticeNo, myUserDetails.getMemberNo());
        }
        model.addAttribute("read", boardNoticeService.findByBoardNoticeNo(boardNoticeNo));

        return "board/notice/read";
    }

    @GetMapping("/board/notice/list")
    public String noticeList() {
        return "board/notice/list";
    }
}
