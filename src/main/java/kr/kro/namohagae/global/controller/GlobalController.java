package kr.kro.namohagae.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

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

    @GetMapping("/puching/chatroom")
    public void chatroom() {
    };



}
