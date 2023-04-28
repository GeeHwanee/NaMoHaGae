package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.mall.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {
    private final ProductReviewService service;

    //@GetMapping("/mall/review/write")
    public void write(Integer orderDetailNo, Model model) {
        model.addAttribute("item", service.read(orderDetailNo));
    }

/*    @PostMapping("/mall/review/write")
    public String write(ProductReviewDto.Write dto, Principal principal) {
        service.write(dto, principal.getName());
        return "redirect:/mall/order/list";
    }*/
}
