package kr.kro.namohagae.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {
    @GetMapping("/member/alarm_list")
    public void list() {
    }

    @GetMapping("")
    public void read() {
    }
}
