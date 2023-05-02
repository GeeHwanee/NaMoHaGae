package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardTownService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1")
@RestController
public class BoardTownController {

    @Autowired
    BoardTownService boardTownService;

    @GetMapping("/board/town/list")
    public ResponseEntity<?> boardTownList(@RequestParam("townNo") Integer townNo) {

        return ResponseEntity.ok(boardTownService.boardTownList(townNo));
    }
}
