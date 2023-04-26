package kr.kro.namohagae.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.namohagae.global.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/*
    작성자: 박지환

*/

@Controller
public class GlobalController {

    // [Global 파트]--------------------------------------------------------------------
    @GetMapping(value = {"/", "/main"})
    public String main(){
        return "main.html";
    }

    @GetMapping("/puching/main")
    public String puchingMain() {
        return "/puching/main.html";
    }

    @GetMapping("/mall/main")
    public String mallMain(){
        return "/mall/main.html";
    }

    @GetMapping("/board/main")
    public String boardMain(){
        return "/board/main.html";
    }

    @GetMapping("/member/main")
    public String memberMain(){ return "/member/main.html";}

    @GetMapping("/login")
    public void login(){}

    @GetMapping(value = "/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> viewPhoto(String name, HttpServletRequest req){
        String folder = Constants.IMAGE_FOLDER;
        File file = new File(folder, name);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            MediaType type = MediaType.parseMediaType(contentType);
            return ResponseEntity.ok().contentType(type).body(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    // [회원 파트]--------------------------------------------------------------------
    @GetMapping("/member/join")
    public void join(){}

    @GetMapping("/member/find")
    public void find(){}

    @GetMapping("/member/alarm")
    public void alarm(){}

    // [퍼칭 파트]--------------------------------------------------------------------
    @GetMapping("/puching/chatroom")
    public void chatroom() {
    }

    // [게시판 파트]--------------------------------------------------------------------
    @GetMapping("/board/free/list")
    public void list(){}

    // [쇼핑몰 파트]--------------------------------------------------------------------
    @GetMapping("/mall/cart")
    public void cart(){
    }

    // [관리자 파트]--------------------------------------------------------------------



    // -------------------------------------------------------------------------------

}
