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
        List<PuchingDto.readTown> list = service.findAllTown();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value="/puching/userlist")
    public ResponseEntity<List<PuchingDto.readUser>> userList(Double latitude,Double longitude,Integer pageNum,Integer pageSize) { //들어온 숫자 검증
        System.out.println(latitude);
        System.out.println(longitude);

        return ResponseEntity.ok().body(service.readUsers(latitude,longitude,pageNum,pageSize));
    }


}