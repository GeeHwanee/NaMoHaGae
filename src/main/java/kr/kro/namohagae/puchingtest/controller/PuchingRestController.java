package kr.kro.namohagae.puchingtest.controller;

import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import kr.kro.namohagae.puchingtest.service.PuchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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
    public ResponseEntity<List<PuchingDto.readUser>> userList(Double latitude, Double longitude, Integer pageNum, Integer pageSize, Principal principal) { //들어온 숫자 검증
        String userEmail=principal.getName();
        System.out.println(userEmail);
        return ResponseEntity.ok().body(service.readUsers(latitude,longitude,pageNum,pageSize,userEmail));
    }

    @GetMapping(value="/puching/checkpuching")
    public ResponseEntity<Void> checkpuching(Principal principal,Integer receiverNo){
        System.out.println(principal.getName());
        System.out.println(receiverNo);
      Integer result = service.checkpuching(principal.getName(),receiverNo);
      if (result==0){
          return ResponseEntity.ok().body(null);
      }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    };


}