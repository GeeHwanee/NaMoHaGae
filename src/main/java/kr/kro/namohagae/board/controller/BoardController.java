package kr.kro.namohagae.board.controller;


import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final MemberDao memberDao;
    private final BoardService boardService;
    private final BoardInsightService boardInsightService;
    private final CommentService commentService;

    @GetMapping("/free/list")
    public String list() {
        return "board/free/list";
    }

    @GetMapping("/free/write")
    public String boardWrite() {
        return "board/free/write";
    }

    @PostMapping("/free/write")
    public String boardFreeWrite(BoardDto.Write boardDto, @AuthenticationPrincipal MyUserDetails myUserDetails)  {
        boardService.save(boardDto,myUserDetails.getMemberNo());
        return "redirect:/board/free/list";
    }

    @GetMapping("/free/read")
    public String boardFreeReadData(@RequestParam("boardNo") Integer boardNo,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page ,
                                    @RequestParam(value ="searchName", defaultValue = "") String searchName,
                                    @RequestParam(value ="change", defaultValue = "1") int change,Model model,Principal principal, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        Boolean isRead = boardInsightService.existsByBoardNoAndMemberNo(boardNo, myUserDetails.getMemberNo());
        if(!isRead){
            boardInsightService.save(boardNo, myUserDetails.getMemberNo());
        }

        model.addAttribute("change", change);
        model.addAttribute("searchName", searchName);
        model.addAttribute("modify",memberDao.findNoByUsername(principal.getName()));
        model.addAttribute("comment", commentService.commentList(boardNo));
        model.addAttribute("board", boardService.readByBoardNo(boardNo));
        model.addAttribute("page", page);

        return "board/free/read";

    }

    @GetMapping("/free/delete")
    public String boardDeleteData(Integer boardNo) {

        boardService.boardDeleteData(boardNo);

        return "redirect:/board/free/list";
    }

    @GetMapping("/free/modify")
    public String boardModify(Integer boardNo,Model model) {

        model.addAttribute("board", boardService.boardFreeReadData(boardNo));

        return "board/free/modify";
    }

    @PostMapping("/free/update")
    public String boardUpdateData(Board board) {

        boardService.boardUpdateData(board);

        return "redirect:/board/free/list";
    }

    @GetMapping("/free/member/List")
    public ResponseEntity<?> memberList(@RequestParam(defaultValue="1")Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo = myUserDetails.getMemberNo();
        return  ResponseEntity.ok(boardService.memberList(pageno,memberNo));
    }
}
