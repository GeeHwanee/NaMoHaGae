package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class NotificationRestController {
    private final NotificationService notificationService;
    @GetMapping(value="/notification/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails) {
        return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(notificationService.findAll(pageno,myUserDetails.getMemberNo()));
    }

    @PutMapping("/notification/read")
    public ResponseEntity<String> updateNotificationRead(Integer notificationNo) {
        // 알람의 읽음 여부를 업데이트하는 로직 작성
        notificationService.read(notificationNo);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping(value = "/notification/aside/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationDto.FindAll>> printNotificationList(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        if (myUserDetails != null) {
            return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(notificationService.printNotificationList(myUserDetails.getMemberNo()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
}
