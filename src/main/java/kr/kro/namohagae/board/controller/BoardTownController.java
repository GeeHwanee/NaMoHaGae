package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.BoardTownDto;
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

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BoardTownController {
    private final BoardService boardService;
    private final BoardTownService boardTownService;
    private final MemberDao memberDao;
    private final TownService townService;
    private final CommentService commentService;
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
    public String townList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, Principal principal){

        model.addAttribute("town",townService.findFuck(memberDao.findNoByUsername(principal.getName())));
        model.addAttribute("logintownNo",myUserDetails.getTownNo());

        return "board/town/list";
    }

}
