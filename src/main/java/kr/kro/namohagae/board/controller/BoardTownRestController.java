package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1")
@RestController
public class BoardTownRestController {

    @Autowired
    BoardTownService boardTownService;
    @Autowired
    BoardService boardService;



    @GetMapping("/board/town/update")
    public ResponseEntity<?> boardTownUpdate(Board board) {
            boardTownService.townUpdateData(board);
        return ResponseEntity.ok().body("변경성공");
    }


}
