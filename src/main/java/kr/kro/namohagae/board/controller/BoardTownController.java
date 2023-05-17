package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.dto.BoardTownListDto;
import kr.kro.namohagae.board.dto.PageDto;
import kr.kro.namohagae.board.dto.ResponseDto;
import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import kr.kro.namohagae.board.service.BoardTownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                           @RequestParam(value ="page", required = false, defaultValue = "1") int page) {        PageDto pageDTO = boardService.pagingParam(page);
        List<BoardTownListDto> boardTownListDto = boardTownService.boardTownList(townNo,searchName,page);
        ResponseDto responseDto = new ResponseDto(pageDTO,boardTownListDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/board/town/update")
    public ResponseEntity<String> boardTownUpdate(Board board) {
            boardTownService.townUpdateData(board);
        return ResponseEntity.ok().body("변경성공");
    }


}
