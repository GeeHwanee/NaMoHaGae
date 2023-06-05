package kr.kro.namohagae.admin.controller;

import kr.kro.namohagae.global.dto.TownDto;
import kr.kro.namohagae.global.service.TownService;
import kr.kro.namohagae.mall.dto.ProductDto;
import kr.kro.namohagae.mall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AdminRestController {

    private final ProductService productService;
    private final TownService townService;

    // [상품 수정]--------------------------------------------------------------------
    @PostMapping("/admin/product/put")
    public ResponseEntity<ProductDto.Read> productPut(ProductDto.Put dto) {
        System.out.println(dto.toString());
        Integer result = productService.put(dto);
        ProductDto.Read resultDto = productService.read(result);
        return ResponseEntity.ok().body(resultDto);
    }

    // [Town 수정]--------------------------------------------------------------------

    @GetMapping(value = "/admin/town/list",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>townList(Integer pageno,String gu){
        if(gu.trim().equals("")==false){
            System.out.println(gu);
            return ResponseEntity.ok(townService.findAllByGu(pageno,gu));
        }
        System.out.println(gu);

        return ResponseEntity.ok(townService.findAll(pageno));
    }
    @GetMapping("/admin/town/checkDong")
    public ResponseEntity<Boolean> checkTownDong(String townDong){
        return ResponseEntity.ok(townService.checkDong(townDong));
    }

    @PostMapping("/admin/town/save")
    public ResponseEntity<Boolean> townSave(TownDto.save dto){
        return ResponseEntity.ok(townService.save(dto));
    }
    @PostMapping("/admin/town/delete")
    public ResponseEntity<Boolean> towndelete(Integer townNo){
        return  ResponseEntity.ok(townService.delete(townNo));
    }
}
