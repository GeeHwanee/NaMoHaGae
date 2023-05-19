package kr.kro.namohagae.mall.controller;

import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService service;

    @GetMapping("/mall/product/list")
    public String list(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, HttpSession session,
                       @RequestParam(defaultValue="NewProduct") String sortBy, @RequestParam(value="searchProduct", defaultValue = "") String searchProduct,
                       @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(session.getAttribute("msg") != null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }

        ProductDto.Pagination pagination;
        if (!searchProduct.isEmpty()) {
            pagination = service.searchProductName(pageNo, categoryNo, myUserDetails.getMemberNo(), searchProduct);
            model.addAttribute("searchProduct", searchProduct);
        } else {
            pagination = service.findAll(pageNo, categoryNo, sortBy, myUserDetails.getMemberNo());
            model.addAttribute("searchProduct", "");
        }

        model.addAttribute("list", pagination);
        return "mall/product/list";
    }


    @GetMapping("/mall/product/read")
    public String read(Integer productNo, Model model) {
        model.addAttribute("product", service.read(productNo));
        return "mall/product/read";
    }


    @PostMapping("/mall/product/list")
    public String searchProduct(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, @RequestParam(value="searchProduct", defaultValue = "") String searchProduct,
                                @RequestParam(defaultValue="NewProduct") String sortBy, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails, RedirectAttributes redirectAttributes) {
        if (searchProduct != null && !searchProduct.isEmpty()) {
            model.addAttribute("list", service.searchProductName(pageNo, categoryNo, myUserDetails.getMemberNo(), searchProduct));
        }

        redirectAttributes.addAttribute("categoryNo", categoryNo);
        redirectAttributes.addAttribute("sortBy", sortBy);
//        redirectAttributes.addAttribute("searchProduct", "");
        return "redirect:/mall/product/list";
    }
}