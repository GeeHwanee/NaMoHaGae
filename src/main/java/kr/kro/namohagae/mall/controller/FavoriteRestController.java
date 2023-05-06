package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
