package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FavoriteRestController {
    private final FavoriteService service;


    @PatchMapping("/favorite/add")
    public ResponseEntity<?> favorite(Integer productNo, @AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.favorite(productNo, memberNo));
    }

    @GetMapping("/favorite/check")
    public  ResponseEntity<Boolean> checkFavorite(Integer productNo,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo=myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.checkFavorite(productNo, memberNo));
    }
    @GetMapping(value="/favorite/list", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@RequestParam(defaultValue="1") Integer pageno, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo= myUserDetails.getMemberNo();
        return ResponseEntity.ok(service.list(pageno,memberNo));
    }

}
