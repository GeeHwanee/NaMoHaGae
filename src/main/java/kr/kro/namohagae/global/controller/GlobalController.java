package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
    작성자: 박지환

*/

@Controller
public class GlobalController {

    @Autowired
    BoardService boardService;
    // [Global 파트]--------------------------------------------------------------------
    @GetMapping(value = {"/", "/main"})
    public String main(){
        return "main.html";
    }

    @GetMapping("/puching/main")
    public String puchingMain() {
        return "/puching/main.html";
    }

    @GetMapping("/mall/main")
    public String mallMain(){
        return "/mall/main.html";
    }

    @GetMapping("/board/main")
    public String boardMain(){
        return "/board/main.html";
    }

    @GetMapping("/member/main")
    public String memberMain(){ return "/member/main.html";}

    @GetMapping("/login")
    public void login(){}

    // [회원 파트]--------------------------------------------------------------------
    @GetMapping("/member/join")
    public void join(){}

    @GetMapping("/member/find")
    public void find(){}

    @GetMapping("/member/alarm")
    public void alarm(){}

    // [퍼칭 파트]--------------------------------------------------------------------
    @GetMapping("/puching/chatroom")
    public void chatroom() {
    }

    // [게시판 파트]--------------------------------------------------------------------
    @GetMapping("/board/free/list")
    public String boardFreeList(Model model) {

        model.addAttribute("list", boardService.boardFreeList());

        return "board/free/list";
    }
    // [쇼핑몰 파트]--------------------------------------------------------------------
    @GetMapping("/mall/cart")
    public void cart(){
    }

    // [관리자 파트]--------------------------------------------------------------------



    // -------------------------------------------------------------------------------

}
