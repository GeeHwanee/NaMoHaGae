package kr.kro.namohagae.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {


    @GetMapping("/board/main")
    public String boardMain() {

        return "board/free/write";
    }
}
