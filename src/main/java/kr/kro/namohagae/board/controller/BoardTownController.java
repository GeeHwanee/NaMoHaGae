package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BoardTownController {
    private final BoardService boardService;
    private final BoardTownService boardTownService;
    private final MemberDao memberDao;
    private final TownService townService;
    private final CommentService commentService;

}
