package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.service.*;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final BoardService boardService;
    private final BoardNoticeService boardNoticeService;
    @GetMapping("/board/notice/read")
    public String noticeRead(Model model, Integer boardNoticeNo, @AuthenticationPrincipal MyUserDetails myUserDetails){
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
}
