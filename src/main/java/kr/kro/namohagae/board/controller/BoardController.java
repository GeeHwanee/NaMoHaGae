package kr.kro.namohagae.board.controller;



import kr.kro.namohagae.board.dto.BoardDto;

import kr.kro.namohagae.board.dto.BoardLikeDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@Controller
@RequestMapping("/board/free")
public class BoardController {
    @Autowired
    MemberDao memberDao;
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;






    @GetMapping("/write")
    public String boardFreeWrite() {


        return "board/free/write";
    }

    @PostMapping("/writepro")
    public String boardFreeWritePro(BoardDto.write boardDto, Principal principal)  {

        boardService.boardFreeInsertData(boardDto,principal.getName());
        return "redirect:/board/free/list";
    }

    @GetMapping("/read")
    public String boardFreeReadData(@RequestParam("boardNo") Integer boardNo,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page ,Model model,Principal principal) {
        boardService.increaseReadCnt(boardNo);
        boolean isLiked = boardService.isLikeExists(boardNo,memberDao.findNoByUsername(principal.getName()));

        if(isLiked){
            model.addAttribute("good","좋아요취소");
        } else {
            model.addAttribute("good","좋아요");
        }

        model.addAttribute("modify",memberDao.findNoByUsername(principal.getName()));
        model.addAttribute("comment", commentService.commentList(boardNo));
        model.addAttribute("board", boardService.boardFreeReadData(boardNo));
        model.addAttribute("page", page);
        return "board/free/read";

    }

    @GetMapping("/delete")
    public String boardDeleteData(Integer boardNo) {

        boardService.boardDeleteData(boardNo);

        return "redirect:/board/free/list";
    }

    @GetMapping("/modify")
    public String boardModify(Integer boardNo,Model model) {

        model.addAttribute("board", boardService.boardFreeReadData(boardNo));

        return "board/free/modify";
    }

    @PostMapping("/update")
    public String boardUpdateData(Board board) {

        boardService.boardUpdateData(board);

        return "redirect:/board/free/list";
    }

    @PostMapping("/like")
    public ResponseEntity<?> boardLike(Integer boardNo, Integer memberNo){

        boolean isLiked = boardService.isLikeExists(boardNo,memberNo);
        if(isLiked) {
            boardService.removeLike(boardNo,memberNo);
            boardService.badLike(boardNo);


        } else {
            boardService.insertLike(boardNo,memberNo);
            boardService.goodLike(boardNo);
        }

        return ResponseEntity.ok(boardService.boardFreeReadData(boardNo));
    }

        // value 파라미터 이름 required = false 필수가 아니다

    //  멤버번호 이름 꺼내는법
//    @GetMapping("/board/free/read")
//    public void read(@AuthenticationPrincipal MyUserDetails myUserDetails){
//      Intwger memberNo = myUserDetails.getMemberNo();
//      String name = myUserDetails.getUsername();
//
//
//
//
//    }
}
