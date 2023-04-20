package kr.kro.namohagae.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlarmController {
    @GetMapping("/member/alarm_list")
    public void list() {
    }

    @GetMapping("")
    public void read() {
    }
}
