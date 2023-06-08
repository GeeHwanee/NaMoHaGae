package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.service.BoardNoticeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardNoticeRestController {
    private final BoardNoticeService boardNoticeService;

    @GetMapping(value = "/notice/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> noticeList(Integer pageno){
        return ResponseEntity.ok(boardNoticeService.list(pageno));
    }
}
