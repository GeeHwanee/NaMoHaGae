package kr.kro.namohagae.puching.controller;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.puching.dto.ReviewDto;
import kr.kro.namohagae.puching.service.ChatService;
import kr.kro.namohagae.puching.service.PuchingService;
import kr.kro.namohagae.puching.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PuchingController {

    private final MemberDao memberDao;
    private final ChatService chatService;
    private final ReviewService puchingReviewService;
    @Secured("ROLE_DOG")
    @GetMapping("/puching/chatroom")
    public String chatroom(Principal principal, Model model, @RequestParam(defaultValue = "")String receiverEmail) {
        Integer myMemberNo=memberDao.findNoByUsername(principal.getName());
        model.addAttribute("list",chatService.findAllChatRoom(myMemberNo));
        model.addAttribute("mymemberNo",myMemberNo);
        model.addAttribute("startuser",receiverEmail);
        return "puching/chatRoom";
    }
    @Secured("ROLE_DOG")
    @GetMapping("/puching/locationview")
    public String locationview(@RequestParam("lat")Double lat,@RequestParam("lng") Double lng){

        return "puching/locationview";
    };
    @Secured("ROLE_DOG")
    @GetMapping("/puching/reviewwrite")
    public String reviewwrite(@RequestParam("receiverNo")Integer receiverNo,@RequestParam("puchingNo")Integer puchingNo,Principal principal,Model model){
        System.out.println(receiverNo);
        System.out.println(puchingNo);
        ReviewDto.Writeview dto=puchingReviewService.findWriteViewInfo(principal.getName(),receiverNo,puchingNo);
        model.addAttribute("list",dto);
        System.out.println(dto);
        return "puching/reviewwrite";
    }
    @Secured("ROLE_DOG")
    @PostMapping(value="/puching/reviewwrite")
    public String write(ReviewDto.Write dto, Principal principal) {
        System.out.println(dto);
        puchingReviewService.saveReview(principal.getName(),dto);
        return "redirect:/puching/main";
    }
    @Secured("ROLE_DOG")
    @GetMapping(value = "/puching/puching_introduce")
    public String introduce(){
        return "puching/puching_introduce";
    }

    @Secured("ROLE_DOG")
    @GetMapping(value = "/puching/search")
    public String search(){
        return "puching/search";
    }

}
