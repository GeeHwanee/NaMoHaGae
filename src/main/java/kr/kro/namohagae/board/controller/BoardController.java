package kr.kro.namohagae.board.controller;


import jakarta.servlet.http.HttpServletRequest;
import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.entity.Town;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final MemberDao memberDao;
    private final BoardService boardService;
    private final BoardInsightService boardInsightService;
    private final CommentService commentService;
    private final TownService townService;

    @GetMapping(value = {"/free/list", "/town/list"})
    public String list(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest req) {
        String path = req.getRequestURI();
        if (path.contains("/free")) {
            return "board/free/list";
        } else if (path.contains("/town")) {
            Town town = townService.findByMemberNo(myUserDetails.getMemberNo());
            model.addAttribute("townNo",town.getTownNo());
            model.addAttribute("townDong",town.getTownDong());
            return "board/town/list";
        }
        return "redirect:/board/main";
    }

    @GetMapping(value = {"/free/write","/town/write"})
    public String boardWrite(Model model,@AuthenticationPrincipal MyUserDetails myUserDetails ,HttpServletRequest req) {
        String path = req.getRequestURI();
        if (path.contains("/free")) {
            return "board/free/write";
        } else if (path.contains("/town")) {
            Town town = townService.findByMemberNo(myUserDetails.getMemberNo());
            model.addAttribute("townNo",town.getTownNo());
            return "board/town/write";
        }
        return "redirect:/board/main";
    }

    @PostMapping(value = {"/free/write","/town/write"})
    public String boardFreeWrite(BoardDto.Write boardDto, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest req)  {
        String path = req.getRequestURI();
        boardService.save(boardDto, myUserDetails.getMemberNo());
        if (path.contains("/free")) {
            return "redirect:/board/free/list";
        } else if (path.contains("/town")) {
            return "redirect:/board/town/list";
        }
        return "redirect:/board/main";
    }

    @GetMapping(value = {"/free/read", "/town/read"})
    public String boardFreeReadData(Integer boardNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest req) {
        String path = req.getRequestURI();

        Boolean isRead = boardInsightService.existsByBoardNoAndMemberNo(boardNo, myUserDetails.getMemberNo());
        if(!isRead){
            boardInsightService.save(boardNo, myUserDetails.getMemberNo());
        }
        model.addAttribute("board", boardService.readByBoardNo(boardNo));
        model.addAttribute("comment", commentService.commentList(boardNo));

        if (path.contains("/free")) {
            return "board/free/read";
        } else if (path.contains("/town")) {
            return "board/free/read";
        }
        return "redirect:/board/main";
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
