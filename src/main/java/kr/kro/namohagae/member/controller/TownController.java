package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.member.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TownController {

    @Autowired
    private TownService townService;

    @GetMapping("/api/v1/member/join")
    public ResponseEntity<List<Map>> read(@RequestParam String gu){
        System.out.println(gu);
        return ResponseEntity.ok(townService.read(gu));
    }


}
