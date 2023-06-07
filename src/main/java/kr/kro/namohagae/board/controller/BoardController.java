package kr.kro.namohagae.board.controller;


import kr.kro.namohagae.board.dto.BoardDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardList;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/board/free")
public class BoardController {
    @Autowired
    MemberDao memberDao;
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;


    @GetMapping("/list")
    public String paging(Model model,
                         @RequestParam(value ="page", required = false, defaultValue = "1") int page,
                         @RequestParam(value ="searchName", defaultValue = "") String searchName,
                         @RequestParam(value ="change", defaultValue = "1") int change,Integer townNo) {
        List<BoardList> pagingList = boardService.pagingList(searchName,page);
        PageDto pageDTO = boardService.pagingParam(page,0);
        model.addAttribute("change", change);
        if (searchName != null && !searchName.isEmpty()) {
            model.addAttribute("searchName", searchName);
        }
        if(change == 3) {
            model.addAttribute("list",boardService.recommendCountList(searchName, page));
            model.addAttribute("paging", pageDTO);
            System.out.println(boardService.recommendCountList(searchName, page));
        }

        if(change == 2) {
            model.addAttribute("list",boardService.readCountList(searchName, page));
            model.addAttribute("paging", pageDTO);

        }
        if (change == 1) {
            model.addAttribute("list", pagingList);
            model.addAttribute("paging", pageDTO);
        }

        return "board/free/list";
    }



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
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page ,
                                    @RequestParam(value ="searchName", defaultValue = "") String searchName,
                                    @RequestParam(value ="change", defaultValue = "1") int change,Model model,Principal principal) {




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

        model.addAttribute("change", change);
        model.addAttribute("searchName", searchName);
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



        if(boardService.findLike(boardNo,memberNo) == 1) {
            boardService.removeLike(boardNo,memberNo);
            boardService.badLike(boardNo);
        }
        else {
            boardService.updateLike(boardNo,memberNo);
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

    @GetMapping("/member/List")
    public ResponseEntity<?> memberList(@RequestParam(defaultValue="1")Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo = myUserDetails.getMemberNo();
        return  ResponseEntity.ok(boardService.memberList(pageno,memberNo));

    }
}
