package kr.kro.namohagae.global.controller;

import kr.kro.namohagae.global.dto.BlockDto;
import kr.kro.namohagae.global.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BlockRestContoller {
    @Autowired
    private BlockService blockService;
    @PostMapping("/block/save")
    public ResponseEntity<Boolean> save(BlockDto.save dto){
        return  ResponseEntity.ok(blockService.save(dto));
    }
}
