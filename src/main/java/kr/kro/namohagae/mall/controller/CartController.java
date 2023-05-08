package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.CartDto;
import kr.kro.namohagae.mall.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService service;

    // 장바구니 보기
    @GetMapping("/mall/cart/list")
    public String list(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo=myUserDetails.getMemberNo();
        model.addAttribute("map", service.list(memberNo));
        return "/mall/cart/list";
    }

    // 장바구니 삭제
    @PostMapping("/cart/remove")
    public String delete(CartDto.Delete dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Integer memberNo=myUserDetails.getMemberNo();
        service.delete(dto, memberNo);
        return "redirect:/mall/cart/list";
    }

}
