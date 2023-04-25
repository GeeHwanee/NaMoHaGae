package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService service;

    @GetMapping("/mall/product/list")
    public String list(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Integer memberNo, Model model, HttpSession session) {
        System.out.println(categoryNo +"불러옴");
        System.out.println("출력성공");
        if(session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        model.addAttribute("list", service.list(pageNo, categoryNo));
        return "/mall/product/list";
    }

    @GetMapping("/mall/product/read")
    public void read(Integer productNo, Model model) {
        model.addAttribute("product", service.read(productNo));
    }
}
