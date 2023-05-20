package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.global.dto.ReportDto;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReportRestController {
    @Autowired
    private ReportService reportService;
    @GetMapping(value="/report/findAll", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@RequestParam(defaultValue="1") Integer pageno,String nickname) {
        Integer memberNo = reportService.findMemberNoByNickname(nickname);
        if (nickname!=null&&nickname.trim().equals("")==false){
            return ResponseEntity.ok(reportService.findAllByMemberNo(pageno,memberNo));
        }
        return ResponseEntity.ok(reportService.findAll(pageno));
    }
    @PostMapping("report/save")
    public ResponseEntity<Boolean> save(@AuthenticationPrincipal MyUserDetails myUserDetails, ReportDto.Save dto){
        Integer reportMemberNo = myUserDetails.getMemberNo();
        return ResponseEntity.ok(reportService.save(dto,reportMemberNo));
    }
    @PostMapping("/report/delete")
    public ResponseEntity<Boolean> delete(Integer reportNo){
        return ResponseEntity.ok(reportService.delete(reportNo));
    }

}
