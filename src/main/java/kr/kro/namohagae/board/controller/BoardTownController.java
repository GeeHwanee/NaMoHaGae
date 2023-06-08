package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.BoardTownDto;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/board/town/delete")
    public String boardTownDelete(Integer boardNo) {
        boardTownService.townDeleteData(boardNo);

        return "redirect:/board/town/list";
    }


}
