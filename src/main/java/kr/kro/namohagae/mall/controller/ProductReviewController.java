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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {
    private final ProductReviewService service;

    /*
    @GetMapping("/mall/product/review/write")
    public String write(Integer orderDetailNo, Model model) {
        model.addAttribute("item", service.read(orderDetailNo));
        return "/mall/product/review/write";
    }
     */

    @GetMapping("/mall/product/review/write")
    public String write(Integer orderDetailNo, Integer productNo, Model model) {
        List<ProductReviewDto.Write> productInfo = service.findInformationByProductNo(productNo);
        model.addAttribute("item", service.read(orderDetailNo));
        model.addAttribute("productName", productInfo.get(0).getProductName());
        model.addAttribute("productImage", productInfo.get(0).getProductImage());
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
