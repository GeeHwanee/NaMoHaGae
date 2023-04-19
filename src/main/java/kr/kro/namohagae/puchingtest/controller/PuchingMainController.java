package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.puchingtest.service.PuchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PuchingMainController {
    @Autowired
    private PuchingService service;

    @GetMapping("/puching/puching_main")
    public void puchingmain() {
        service.town();
    };

}
