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
    public ResponseEntity<?> findAll(@RequestParam(defaultValue="1") Integer pageno, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(reportService.findAll(pageno,memberNo));
    }
    @PostMapping("report/save")
    public ResponseEntity<Boolean> save(@AuthenticationPrincipal MyUserDetails myUserDetails, ReportDto.Save dto){
        Integer reportMemberNo = myUserDetails.getMemberNo();
        reportService.save(dto,reportMemberNo);
        return ResponseEntity.ok(reportService.save(dto,reportMemberNo));
    }
    @GetMapping(value="/report/findAllByMemberNo", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllByMemberNo(@RequestParam(defaultValue="1") Integer pageno, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(reportService.findAllByMemberNo(pageno,memberNo));
    }
    @PostMapping("/report/delete")
    public ResponseEntity<Boolean> delete(Integer reportNo){
        return ResponseEntity.ok(reportService.delete(reportNo));
    }

}
