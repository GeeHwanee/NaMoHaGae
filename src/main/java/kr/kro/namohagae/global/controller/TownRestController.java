package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.entity.Town;
import kr.kro.namohagae.global.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TownRestController {

    @Autowired
    private TownService townService;

    @GetMapping("/town/find")
    public ResponseEntity<List<TownDto.Read>> viewTownDong(String townGu){
        return ResponseEntity.ok(townService.findTownDongByGu(townGu));
    }

    @GetMapping(value = "/town/dongList",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>dongList(){
        return  ResponseEntity.ok(townService.findDong());
    }
    @PostMapping("/admin/town/save")
    public ResponseEntity<Boolean> townSave(TownDto.save dto){
        return ResponseEntity.ok(townService.save(dto));
    }
    @PostMapping("/admin/town/delete")
    public ResponseEntity<Boolean> towndelete(Integer townNo){
        return  ResponseEntity.ok(townService.delete(townNo));
    }
    @GetMapping("/admin/town/checkDong")
    public ResponseEntity<Boolean> checkTownDong(String townDong){
        return ResponseEntity.ok(townService.checkDong(townDong));
    }
    @GetMapping(value = "/admin/town/list",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>townList(Integer pageno,String gu){
        if(gu.trim().equals("")==false){
            System.out.println(gu);
        return ResponseEntity.ok(townService.findAllByGu(pageno,gu));
        }
        System.out.println(gu);

        return ResponseEntity.ok(townService.findAll(pageno));
    }
}