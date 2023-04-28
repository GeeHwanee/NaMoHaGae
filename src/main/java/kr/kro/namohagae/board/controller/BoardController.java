package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board/free")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/write")
    public String boardFreeWrite() {

        return "board/free/write";
    }

    @PostMapping("/writepro")
    public String boardFreeWritePro(Board board)  {
        boardService.boardFreeInsertData(board);
        return "redirect:/board/free/list";
    }

    @GetMapping("/read")
    public String boardFreeReadData(@RequestParam("boardNo") Integer boardNo,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {


        model.addAttribute("board", boardService.boardFreeReadData(boardNo));
        model.addAttribute("page", page);
        return "board/free/read";

    }

    @GetMapping("/delete")
    public String boardDeleteData(Integer boardNo) {

        boardService.boardDeleteData(boardNo);

        return "redirect:/board/free/list";
    }

    @GetMapping("/modify")
    public String boardModify(Integer boardNo,Model model) {

        model.addAttribute("board", boardService.boardFreeReadData(boardNo));

        return "board/free/modify";
    }

    @PostMapping("/update")
    public String boardUpdateData(Board board) {

        boardService.boardUpdateData(board);

        return "redirect:/board/free/list";
    }
        // value 파라미터 이름 required = false 필수가 아니다

}
