package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService service;

    @GetMapping("/mall/product/list")
    public String list(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, HttpSession session,
                       @RequestParam(defaultValue="false") boolean orderByProductName, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        if(session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }

        if (orderByProductName) {
            model.addAttribute("list", service.findAllByProductName(pageNo, categoryNo, myUserDetails.getMemberNo()));
        } else {
            model.addAttribute("list", service.list(pageNo, categoryNo, myUserDetails.getMemberNo()));
        }

        return "mall/product/list";
    }



    @GetMapping("/mall/product/read")
    public String read(Integer productNo, Model model) {
        model.addAttribute("product", service.read(productNo));
        return "mall/product/read";
    }



}