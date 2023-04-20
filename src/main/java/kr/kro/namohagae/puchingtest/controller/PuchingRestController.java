package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import kr.kro.namohagae.puchingtest.service.PuchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PuchingRestController {
    @Autowired
    private PuchingService service;

    @GetMapping(value="/puching/townlist")
    public ResponseEntity<List<PuchingDto.readTown>> townlist(){
        List<PuchingDto.readTown> list = service.puchingmap();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value="/puching/userlist")
    public ResponseEntity<List<PuchingDto.readUser>> userlist(Double latitude,Double longitude) {
        List<PuchingDto.readUser> list;
            return null;
    }


}