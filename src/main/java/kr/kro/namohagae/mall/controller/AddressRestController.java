package kr.kro.namohagae.mall.controller;

import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.mall.entity.Address;
import kr.kro.namohagae.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AddressRestController {
    @Autowired
    private AddressService service;
    @GetMapping(value = "/address/list",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> printList(@AuthenticationPrincipal MyUserDetails myUserDetails){
        return  ResponseEntity.ok(service.printAddressAll(myUserDetails.getMemberNo()));
    }
    @PostMapping("/address/delete")
    public ResponseEntity<Boolean> delete(Integer addressNo){
        return ResponseEntity.ok(service.delete(addressNo));
    }

    public ResponseEntity<List<Address>> setDefault(Integer addressNo, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        return ResponseEntity.ok(service.setDefault(addressNo, myUserDetails.getMemberNo()));
    }

}
