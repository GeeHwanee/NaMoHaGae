package kr.kro.namohagae.member.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.member.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class AlarmRestController {
    @Autowired
    private AlarmService service;
    @GetMapping(value="/alarm/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.findAll(pageno,memberNo));
    }

    @PutMapping("/alarm/read/{no}")
    public ResponseEntity<String> updateAlarmRead(@PathVariable("no") int no) {
        // 알람의 읽음 여부를 업데이트하는 로직 작성
        service.read(no);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
