package kr.kro.namohagae.puchingtest.controller;



import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import kr.kro.namohagae.puchingtest.dto.MessageDto;
import kr.kro.namohagae.puchingtest.entity.Message;
import kr.kro.namohagae.puchingtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChatRestController {
    @Autowired
    private ChatService service;


    @GetMapping(value="/findchatlog")
    public ResponseEntity<List<MessageDto.MessageRead>> findByChatLog(Principal principal,Integer receiverNo) {
         String senderEmail= principal.getName();
        return ResponseEntity.ok().body(service.findMessageLog(senderEmail,receiverNo));
    }

    @GetMapping(value="/findchatroom")
    public ResponseEntity<ChatRoomDto.Read> findChatRoomByreceiverNo(Principal principal,String receiverEmail){


        return ResponseEntity.ok().body(service.findChatRoom(principal.getName(),receiverEmail));
    }

    @GetMapping(value = "/existchatroom")
    public ResponseEntity<ChatRoomDto.Read> existchatroom(Principal principal,String receiverEmail){

        return ResponseEntity.ok().body(service.existchatRoom(principal.getName(),receiverEmail));
    }

    @PostMapping(value = "/savechatimage")
    public ResponseEntity<Message> saveImage(MultipartFile file, Principal principal, Integer receiverNo){
      System.out.println("saveImage실행");
      String userEmail=principal.getName();
       Message message= service.saveImage(file,userEmail,receiverNo);

        return ResponseEntity.ok().body(message);
    };




}
