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

//    @GetMapping("/mall/product/list")
//    public void list(){
//    }

    //@GetMapping("/mall/product/read")
    //public void read(){
    //}


    /*
    @GetMapping("/mall/product/list")
    public String list(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, HttpSession session) {
        System.out.println(categoryNo +"번 상품리스트 페이지");
        System.out.println("출력성공");
        if(session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        model.addAttribute("list", service.list(pageNo, categoryNo));
        return "/mall/product/list";
    }

    @GetMapping("/mall/product/list_test")
    public String list_test(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, HttpSession session) {
        System.out.println(categoryNo +"번 상품리스트 페이지");
        System.out.println("이름정렬");
        if(session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }
        model.addAttribute("list", service.findAllByProductName(pageNo, categoryNo));
        return "/mall/product/list_test";
    }
     */
    @GetMapping("/mall/product/list")
    public String list(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, HttpSession session,
                       @RequestParam(defaultValue="false") boolean orderByProductName) {
        System.out.println(categoryNo +"번 상품리스트 페이지");

        if(session.getAttribute("msg")!=null) {
            model.addAttribute("msg", session.getAttribute("msg"));
            session.removeAttribute("msg");
        }

        if (orderByProductName) {
            System.out.println("이름정렬");
            model.addAttribute("list", service.findAllByProductName(pageNo, categoryNo));
        } else {
            System.out.println("최신순정렬");
            model.addAttribute("list", service.list(pageNo, categoryNo));
        }

        return "/mall/product/list";
    }



    @GetMapping("/mall/product/read")
    public String read(Integer productNo, Model model) {
        model.addAttribute("product", service.read(productNo));
        return "/mall/product/read";
    }



}