package kr.kro.namohagae.puchingtest.controller;


import kr.kro.namohagae.puchingtest.dto.ChatDto;
import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChatRestController {
    @Autowired
    private ChatService service;
    @GetMapping("/findchatroom")
    public ResponseEntity<?> findByChatRoom(Principal principal,Integer memberNo){
        String username = principal.getName();
        List<ChatDto.ChatRoomRead> list =service.findByChatRoom(memberNo);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/findchatlog")
    public ResponseEntity<?> findByChatLog(Principal principal,Integer memberNo,Integer memberNo2) {
        String username = principal.getName();


        return null;
    }

}
