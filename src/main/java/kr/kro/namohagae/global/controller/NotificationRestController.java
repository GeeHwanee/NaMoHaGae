package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NotificationRestController {
    @Autowired
    private NotificationService service;
    @GetMapping(value="/notification/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        return ResponseEntity.ok(service.findAll(pageno,myUserDetails.getMemberNo()));
    }

    @PutMapping("/notification/read")
    public ResponseEntity<String> updateNotificationRead(Integer notificationNo) {
        // 알람의 읽음 여부를 업데이트하는 로직 작성
        service.read(notificationNo);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/notification/aside/list")
    public ResponseEntity<List<NotificationDto.FindAll>> printNotificationList(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (myUserDetails != null) {
            return ResponseEntity.ok(service.printNotificationList(myUserDetails.getMemberNo()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

    }
}
