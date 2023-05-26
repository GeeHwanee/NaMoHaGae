package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.BoardTownListDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.dto.ResponseDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import kr.kro.namohagae.global.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RequestMapping("/api/v1")
@RestController
public class BoardTownController {

    @Autowired
    BoardTownService boardTownService;
    @Autowired
    BoardService boardService;

    @GetMapping("/board/town/list")
    public ResponseEntity<?> boardTownList(@RequestParam("townNo") Integer townNo,
                                           @RequestParam(value ="searchName",  defaultValue = "") String searchName,
                                           @RequestParam(value ="page", required = false, defaultValue = "1") int page) {
        PageDto pageDTO = boardService.pagingParam(page,townNo);

        List<BoardTownListDto> boardTownListDto = boardTownService.boardTownList(townNo,searchName,page);
        List<BoardTownListDto> boardTownReadCountDto = boardTownService.boardTownReadCountList(townNo, searchName, page);
        List<BoardTownListDto> boardTownRecommendCountDto = boardTownService.boardTownRecommendCountList(townNo, searchName, page);

            ResponseDto responseDto = new ResponseDto(pageDTO,boardTownListDto,boardTownReadCountDto,boardTownRecommendCountDto);
            return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/board/town/update")
    public ResponseEntity<?> boardTownUpdate(Board board) {
            boardTownService.townUpdateData(board);
        return ResponseEntity.ok().body("변경성공");
    }


}
