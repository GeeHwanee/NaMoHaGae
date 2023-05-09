package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puchingtest.dao.ChatDao;
import kr.kro.namohagae.puchingtest.dao.Puchingdao;
import kr.kro.namohagae.puchingtest.dto.ChatRoomDto;
import kr.kro.namohagae.puchingtest.dto.MessageDto;
import kr.kro.namohagae.puchingtest.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private ChatDao cdao;
    @Autowired
    private Puchingdao pdao;
    @Autowired
    private MemberDao mdao;



    public List<ChatRoomDto.Read> findAllChatRoom(Integer myMemberNo) {
        List<ChatRoomDto.Read> list = cdao.findAllChatRoom(myMemberNo);
        return list;
    }

    //나중에 파라미터 값을 멤버 넘버로 받도록 고치자
    public Integer saveTextMessage(String senderEmail, String receiverEmail, String messageContent,String messageType){
        Integer senderNo=mdao.findNoByUsername(senderEmail);
        Integer receiverNo=mdao.findNoByUsername(receiverEmail);
        Message message =  new MessageDto.MessageSave(senderNo,receiverNo,messageContent).toEntity(messageType);
            cdao.saveMessage(message);
        Boolean a= cdao.existsByChatRoom(senderNo,receiverNo);
        System.out.println(a);
        System.out.println("12312312312313131232131231232131");
        if(cdao.existsByChatRoom(senderNo,receiverNo)==false){

            cdao.saveChatRoom(senderNo,receiverNo);
            cdao.saveChatRoom(receiverNo,senderNo);  //첫 메세지시 채팅방 2개 생성 각자 채팅방이 생성
            return 2;
        }
        return 1;
    }

    public List<MessageDto.MessageRead> findMessageLog(String senderEmail,Integer receiverNo){
            Integer senderNo = mdao.findNoByUsername(senderEmail);
        return  cdao.findAllMessageByReceiverNo(senderNo,receiverNo);
    }

    public ChatRoomDto.Read findChatRoom(String userEmail,String receiverEmail) {
        Integer userNo =mdao.findNoByUsername(userEmail);
        Integer receiverNo= mdao.findNoByUsername(receiverEmail);
        return cdao.findChatRoom(userNo,receiverNo);
    }

    public ChatRoomDto.Read existchatRoom(String username,String receiverEmail) {
        Integer userNo= mdao.findNoByUsername(username);
        Integer receiverNo= mdao.findNoByUsername(receiverEmail);
            if(!cdao.existsByChatRoom(userNo,receiverNo)) {
             return cdao.findChatRoomByReceiverNo(receiverNo);
            }
        return null;
    }
    public Message saveImage(MultipartFile image,String userEmail,Integer receiverNo){
        Integer senderNo=mdao.findNoByUsername(userEmail);
        String imageName="default.jpg";
        if(image!=null && !image.isEmpty()) {
            int postionOfDot = image.getOriginalFilename().lastIndexOf(".");
            String ext = image.getOriginalFilename().substring(postionOfDot);
            String currentDir = System.getProperty("user.dir")+"/";
            System.out.println(currentDir);
            String imagePath = currentDir+ ImageConstants.IMAGE_CHAT_FOLDER;
            imageName=UUID.randomUUID()+ext;
            File file = new File(imagePath, imageName);
            try {
                image.transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        String img = "image";
        Message message= new MessageDto.ImageMessageSave(senderNo,receiverNo).toEntity(img,imageName);
        cdao.saveImage(message);

    return message;
    }

    public MessageDto.PuchingMessageRead savePuchingMessage(MessageDto.PuchingMessageSave dto,String senderEmail){
        Integer senderNo=mdao.findNoByUsername(senderEmail);
        Integer receiverNo= mdao.findNoByUsername(dto.getReceiverUsername());
        String content=""; //태그 포함 저장
        Message message=dto.toEntity(senderNo,receiverNo,content);
        //Integer messageNo=cdao.savePuchingMessage(message);
        //Integer puchingNo=pdao.savePuchingReq(messageNo);


        //MessageDto.PuchingMessageRead readDto= new MessageDto.PuchingMessageRead(content,messageNo);
        return null;//readDto; //퍼칭 메세지를 저장후 메세지 번호를 리턴한걸 퍼칭도 저장후 퍼칭번호에 메세진 번호 넣어서 저장 후 퍼칭번호를 리턴
    };
}
