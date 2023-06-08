package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.*;
import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.KnowledgeService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.util.constants.BoardType;
import kr.kro.namohagae.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class KnowledgeRestController {
    private final KnowledgeService knowledgeService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final BoardInsightService boardInsightService;

    @PostMapping("/knowledge/answer/write")
    public ResponseEntity<String> answerWrite(KnowledgeAnswerDto.Write dto, @AuthenticationPrincipal MyUserDetails myUserDetails){

        System.out.println(dto.toString());
        Boolean result = knowledgeService.answerSave(dto, myUserDetails.getMemberNo());
        if (result) {
           return ResponseEntity.ok().body("성공");
        }else{
           return ResponseEntity.status(HttpStatus.CONFLICT).body("실패");
        }
    }
    @GetMapping(value = "/board/knoledge/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> questionList(@RequestParam(defaultValue="1")Integer pageno){
        return ResponseEntity.ok(knowledgeService.questionFindAll(pageno));
    }
    @GetMapping("/knowledge/main")
    public ResponseEntity<?> boardTownList(
                                           @RequestParam(value ="searchName",  defaultValue = "") String searchName,
                                           @RequestParam(value ="page", required = false, defaultValue = "1") int page,Integer townNo) {
        PageDto pageDTO = boardService.pagingParam(page,0);

        List<KnowledgeMainDto> knowledgeMainDto = knowledgeService.waitList(searchName,page);

        ResponseDto.waitList responseDto = new ResponseDto.waitList(pageDTO,knowledgeMainDto);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/knowledge/answer/list")
    public ResponseEntity<List<KnowledgeAnswerDto.Read>>answerList(Integer questionNo){
        System.out.println(questionNo);
        return ResponseEntity.ok().body(knowledgeService.answerFindAll(questionNo));
    }

    @PostMapping("/knowledge/answer/select")
    public ResponseEntity<String> answerSelect(Integer answerNo, Integer point){
        Boolean result = knowledgeService.answerUpdate(answerNo, point);
        if (result) {
            return ResponseEntity.ok("성공");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("실패");
        }
    }
    @PostMapping("/knowledge/answer/delete")
    public ResponseEntity<String> answerDelete(Integer answerNo){
        Boolean result = knowledgeService.answerDelete(answerNo);
        if (result) {
            return ResponseEntity.ok("성공");
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("실패");
        }
    }

    @PostMapping("/knowledge/answer/recommend")
    public ResponseEntity<?> answerRecommend(Integer answerNo, Integer boardMemberNo, @AuthenticationPrincipal MyUserDetails myUserDetails){
        Boolean isRead = boardInsightService.existsByBoardNoAndMemberNo(answerNo, myUserDetails.getMemberNo());
        if(!isRead){
            boardInsightService.save(answerNo, myUserDetails.getMemberNo());
        }
        boardInsightService.updateBoardRecommendEnabled(BoardType.KNOWLEDGE_ANSWER, answerNo, myUserDetails.getMemberNo());
        return ResponseEntity.ok().body(boardInsightService.findBoardRecommendEnabled(answerNo, myUserDetails.getMemberNo()));
    }

    @GetMapping(value = "/member/knowledge/question",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> myQuestionList(Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo = myUserDetails.getMemberNo();
        return  ResponseEntity.ok(knowledgeService.myQusetionList(pageno,memberNo));
    }
    @GetMapping("/member/knowledge/answer")
    public ResponseEntity<?> myAnswerList(Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo = myUserDetails.getMemberNo();
        return  ResponseEntity.ok(knowledgeService.myAnswerList(pageno,memberNo));
    }
}
