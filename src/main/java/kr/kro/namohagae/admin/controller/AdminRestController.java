package kr.kro.namohagae.admin.controller;

import kr.kro.namohagae.global.dto.BlockDto;
import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.service.BlockService;
import kr.kro.namohagae.global.service.ReportService;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    private final MemberService memberService;
    private final ProductService productService;
    private final TownService townService;
    private final BlockService blockService;
    private final ReportService reportService;

    // [Main 회원 조회]--------------------------------------------------------------------
    @GetMapping("/member/find")
    public ResponseEntity<?> memberList(@RequestParam(defaultValue = "") String searchName){
        return ResponseEntity.ok().body(memberService.preview(searchName));
    }
    @GetMapping("/member/information")
    public ResponseEntity<MemberDto.Read> memberRead(Integer memberNo){
        return ResponseEntity.ok().body(memberService.read(memberNo));
    }
    @PostMapping("/member/disabled")
    public ResponseEntity<Boolean> memberDisabled(Integer memberNo){
        return ResponseEntity.ok().body(memberService.updateMemberEnabled(memberNo));
    }

    // [상품 수정]--------------------------------------------------------------------
    @PostMapping("/product/put")
    public ResponseEntity<ProductDto.Read> productPut(ProductDto.Put dto) {
        Integer result = productService.put(dto);
        ProductDto.Read resultDto = productService.read(result);
        return ResponseEntity.ok().body(resultDto);
    }
    @PostMapping("/product/recommend")
    public ResponseEntity<Boolean> productRecommend(Integer productNo){
        return ResponseEntity.ok().body(productService.productRecommend(productNo));
    }


    // [Town 수정]--------------------------------------------------------------------

    @GetMapping(value = "/town/list",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>townList(Integer pageno,String gu){
        if(!gu.trim().equals("")){
            return ResponseEntity.ok(townService.findAllByGu(pageno,gu));
        }
        return ResponseEntity.ok(townService.findAll(pageno));
    }
    @GetMapping("/town/checkDong")
    public ResponseEntity<Boolean> checkTownDong(String townDong){
        return ResponseEntity.ok(townService.checkDong(townDong));
    }
    @PostMapping("/town/save")
    public ResponseEntity<Boolean> townSave(TownDto.save dto){
        return ResponseEntity.ok(townService.save(dto));
    }
    @PostMapping("/town/delete")
    public ResponseEntity<Boolean> townDelete(Integer townNo){
        return  ResponseEntity.ok(townService.delete(townNo));
    }

    // [Report & Block]--------------------------------------------------------------------
    @PostMapping("/block/save")
    public ResponseEntity<Boolean> blockSave(BlockDto.save dto){
        return  ResponseEntity.ok(blockService.save(dto));
    }

    @GetMapping(value="/report/findAll", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(@RequestParam(defaultValue="1") Integer pageno, String nickname) {
        Integer memberNo = reportService.findMemberNoByNickname(nickname);
        if (memberNo!=null&&nickname.trim().equals("")==false){
            return ResponseEntity.ok(reportService.findAllByMemberNo(pageno,memberNo));
        }
        return ResponseEntity.ok(reportService.findAll(pageno));
    }

    @PostMapping("/report/delete")
    public ResponseEntity<Boolean> delete(Integer reportNo){
        return ResponseEntity.ok(reportService.delete(reportNo));
    }

}
