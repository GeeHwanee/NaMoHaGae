package kr.kro.namohagae.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.namohagae.board.service.BoardInsightService;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.CommentService;
import kr.kro.namohagae.global.security.MyUserDetails;
import kr.kro.namohagae.global.util.constants.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board")
public class BoardRestController {

    private final BoardService boardService;
    private final BoardInsightService boardInsightService;
    private final CommentService commentService;

    @GetMapping(value = {"/free/list", "/town/list"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>preview(@RequestParam(defaultValue = "0") Integer townNo, String searchName, @RequestParam(defaultValue = "") String sorting, @RequestParam(defaultValue = "1") Integer pageNo){
        return ResponseEntity.ok().body(boardService.preview(townNo, searchName, sorting, pageNo));
    }

    @PostMapping(value = {"/free/recommend", "/town/recommend"})
    public ResponseEntity<?> recommend(Integer boardNo, @AuthenticationPrincipal MyUserDetails myUserDetails, HttpServletRequest req){
        String path = req.getRequestURI();
        BoardType boardType = null;
        if (path.contains("/free")) {
            boardType = BoardType.FREE;
        } else if (path.contains("/town")) {
            boardType = BoardType.TOWN;
        }
        Boolean result = boardInsightService.updateBoardRecommendEnabled(boardType, boardNo, myUserDetails.getMemberNo());
        return ResponseEntity.ok().body(result);
    }


}
