package kr.kro.namohagae.puching.service;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puching.dao.ChatDao;
import kr.kro.namohagae.puching.dao.Puchingdao;
import kr.kro.namohagae.puching.dto.PuchingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuchingService {
    @Autowired
    private Puchingdao pdao;
    @Autowired
    private MemberDao mdao;
    @Autowired
    private ChatDao cdao;

    public List<PuchingDto.readTown> findAllTown() {
        System.out.println("퍼칭서비스-puchingmap실행");
        List<PuchingDto.readTown> list = pdao.findAllTown();
        System.out.println("퍼칭서비스-puchingmap완료");
        return list;
    }

    public List<PuchingDto.readUser> readUsers(Double latitude,Double longitude,Integer pageNum,Integer pageSize,String userEmail){
        Integer startrownum=1+((pageNum-1)*10);
        Integer endrownum=pageSize*pageNum;

        Integer memberNo=mdao.findNoByUsername(userEmail);
        List<PuchingDto.readUser> list =pdao.findByUsers(ImageConstants.IMAGE_PROFILE_URL,latitude,longitude,startrownum,endrownum,memberNo);

        return  list;
    }

    public Integer checkpuching(String userEmail,Integer receiverNo){
        Integer senderNo= mdao.findNoByUsername(userEmail);
        Integer result=pdao.checkPuching(senderNo,receiverNo);

        return result;
    }
    public Integer checkAcceptPuching(String senderEmail,Integer receiverNo){
        Integer senderNo=mdao.findNoByUsername(senderEmail);
        Integer messageNo=cdao.findPuchingMessageNo(senderNo,receiverNo,"puching");
            if(messageNo==null || messageNo==0){
                return 1;
            }

            String status=pdao.checkPuchingStatus(messageNo);                       //퍼칭상태가 수락상태인지 확인하는거
            Integer result=pdao.checkWritePuchingReviewBysenderNo(senderNo,messageNo); //내가 해당퍼칭에 리뷰를 작성했는지 확인하는거
             System.out.println(result);
            if(status.equals("수락") && result==0){
                //수락상태인 퍼칭메세지를 가지고 있고 그퍼칭번호로 작성한 리뷰중에 내가 작성한게 없으면 작성이 가능한 상태다
                return 0;
            }

        return 1;
    };

    public Integer findPuchingNo(String senderEmail,Integer receiverNo){
        Integer senderNo=mdao.findNoByUsername(senderEmail);
        return pdao.findPuchingNoBySenderNoAndReceiverNo(senderNo,receiverNo);
    };


}

