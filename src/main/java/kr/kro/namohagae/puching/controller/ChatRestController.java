package kr.kro.namohagae.puching.controller;



import kr.kro.namohagae.puching.dto.ChatRoomDto;
import kr.kro.namohagae.puching.dto.MessageDto;
import kr.kro.namohagae.puching.entity.Message;
import kr.kro.namohagae.puching.service.ChatService;
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
    public ResponseEntity<ChatRoomDto.Read> findChatRoomByReceiverNo(Principal principal,String receiverEmail){
        return ResponseEntity.ok().body(service.findChatRoom(principal.getName(),receiverEmail));
    }

    @GetMapping(value = "/existchatroom")
    public ResponseEntity<ChatRoomDto.Read> existChatroom(Principal principal,String receiverEmail){

        return ResponseEntity.ok().body(service.existchatRoom(principal.getName(),receiverEmail));
    }

    @PostMapping(value = "/savechatimage")
    public ResponseEntity<Message> saveImage(MultipartFile file, Principal principal, Integer receiverNo){
      System.out.println("saveImage실행");
      String userEmail=principal.getName();
       Message message= service.saveImage(file,userEmail,receiverNo);

        return ResponseEntity.ok().body(message);
    };

    @PostMapping(value = "/sendpuching")
    public ResponseEntity<Message> savePuchingMessage(Principal principal,MessageDto.PuchingMessageSave dto){
        return ResponseEntity.ok().body(service.savePuchingMessage(dto,principal.getName()));
    };




}
