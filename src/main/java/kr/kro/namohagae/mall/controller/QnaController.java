package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class QnaController {
    private final QnaService service;

    @GetMapping("/mall/product/qna/write")
    public String write(Integer productNo,Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        ProductDto.Read product = service.read(productNo);
        if(myUserDetails!=null) {
            Integer memberNo = myUserDetails.getMemberNo();
            model.addAttribute("writer", memberNo);
        }
        model.addAttribute("item", product);
        return "/mall/product/qna/write";
    }

    @PostMapping("/mall/product/qna/write")
    public String write(QnaDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(myUserDetails!=null) {
            Integer memberNo = myUserDetails.getMemberNo();
            service.write(dto, memberNo);
        }
        return "redirect:/mall/product/read?productNo=" + dto.getProductNo();
    }
}
