package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductReviewDto;
import kr.kro.namohagae.mall.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {
    private final ProductReviewService service;

    @GetMapping("/mall/product/review/write")
    public String write(Integer productOrderDetailNo, Model model) {
        model.addAttribute("item", service.read(productOrderDetailNo));
        return "/mall/product/review/write";
    }


    @PostMapping("/mall/product/review/write")
    public String write(ProductReviewDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(myUserDetails!=null) {
            Integer memberNo = myUserDetails.getMemberNo();
            service.write(dto, memberNo);
        }
        return "redirect:/mall/order/list";
    }
}
