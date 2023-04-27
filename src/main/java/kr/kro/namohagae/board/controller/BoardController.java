package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/board/free/write")
    public String boardFreeWrite() {

        return "board/free/write";
    }

    @PostMapping("/board/free/writepro")
    public String boardFreeWritePro(Board board, @RequestParam("file") MultipartFile file) throws Exception {
        boardService.boardFreeInsertData(board, file);
        return "redirect:/board/free/list";
    }

    @GetMapping("/board/free/read")
    public String boardFreeReadData(Model model,Integer boardNo) {


        model.addAttribute("board", boardService.boardFreeReadData(boardNo));

        return "board/free/read";

    }
}
