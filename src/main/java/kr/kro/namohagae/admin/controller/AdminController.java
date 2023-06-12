package kr.kro.namohagae.admin.controller;

import kr.kro.namohagae.board.dto.BoardNoticeDto;
import kr.kro.namohagae.board.service.BoardNoticeService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.dto.QnaDto;
import kr.kro.namohagae.mall.service.ProductService;
import kr.kro.namohagae.mall.service.QnaService;
import kr.kro.namohagae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final MemberService memberService;

    private final BoardNoticeService boardNoticeService;
    private final TownService townService;
    private final QnaService qnaService;

    private final ProductService productService;



    // [관리자 파트]--------------------------------------------------------------------
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/list")
    public String adminNoticeList(Model model){
        model.addAttribute("list", boardNoticeService.preview());
        return "admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/write")
    public String adminNoticeWrite(){
        return "admin/notice/write";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/notice/write")
    public String adminNoticeWrite(BoardNoticeDto.Write dto){
        boardNoticeService.addNotice(dto);

        return "redirect:/admin/notice/list";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/read")
    public String adminNoticeRead(Model model,Integer boardNoticeNo) {
        model.addAttribute("read", boardNoticeService.findByBoardNoticeNo(boardNoticeNo));
        return "admin/notice/read";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/delete")
    public String adminNoticeDelete(Integer boardNoticeNo){

        boardNoticeService.delete(boardNoticeNo);
        return "redirect:/admin/notice/list";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/notice/modify")
    public String adminNoticeModify(Model model,Integer boardNoticeNo){

        model.addAttribute("read",boardNoticeService.findByBoardNoticeNo(boardNoticeNo));

        return "admin/notice/modify";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/notice/update")
    public String adminNoticeUpdate(Model model, BoardNoticeDto.Update noticeDto) {
        boardNoticeService.update(noticeDto);
        return "redirect:/admin/notice/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/list")
    public String adminProductList(@RequestParam(defaultValue="1") Integer pageNo, Integer categoryNo, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        model.addAttribute("list",productService.list(pageNo, null, myUserDetails.getMemberNo()));
        return "admin/product/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/report/report")
    public String adminReportList(){ return "admin/report/report";}
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/write")
    public void adminProductWrite(){}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/report/block")
    public void adminBlock(){}
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/product/write")
    public String adminProductWrite(ProductDto.Add dto){
        productService.add(dto);
        return "redirect:/admin/product/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/product/read")
    public String read(Integer productNo, Model model) {
        model.addAttribute("product", productService.read(productNo));
        return "admin/product/read";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/product/delete")
    public String deleteProduct(Integer productNo){
        Boolean result =  productService.delete(productNo);
        if (result) {
            return "redirect:/admin/product/list";
        }else{
            return "redirect:/admin/main";
        }

    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/qna/list")
    public String adminQnaList(Model model){
        model.addAttribute("list", qnaService.readAll());
        return  "admin/qna/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/qna/write")
    public String adminQnaWrite(Integer qnaNo, Model model){
        model.addAttribute("qna",qnaService.print(qnaNo));
        return "admin/qna/write";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/qna/write")
    public String adminQnaWrite(QnaDto.Put dto){
        qnaService.update(dto);
        return "redirect:/admin/qna/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/town/write")
    public void adminTown(){}

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/town/list")
    public void townList(){}
    // -------------------------------------------------------------------------------



}
