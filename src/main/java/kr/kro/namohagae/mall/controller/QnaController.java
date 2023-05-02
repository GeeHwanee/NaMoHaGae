package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class QnaController {
    private final QnaService service;

//    @GetMapping("/mall/product/qna/write")
//    public String write(Integer productNo, Model model) {
//        List<QnaDto.Write> productInfo = service.findInformationByProductNo(productNo);
//        model.addAttribute("productName", productInfo.get(0).getProductName());
//        return "/mall/product/qna/write";
//    }

    // principal 꺼내와야하는데 체크
    @PostMapping("/mall/product/qna/write")
    public String write(QnaDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(myUserDetails!=null) {
            String memberEmail = myUserDetails.getUsername();
            service.write(dto, memberEmail);
        }
        return "redirect:/mall/product/read"; //
    }
}
