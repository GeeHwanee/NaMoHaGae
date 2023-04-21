package kr.kro.namohagae.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

    @GetMapping(value = {"/", "/main"})
    public String main(){
        return "main.html";
    }

    @GetMapping("/puching/puching_main")
    public void puchingmain() {
    };

    @GetMapping("/puching/puching_chatroom")
    public void puchingchatroom() {

    };
}
