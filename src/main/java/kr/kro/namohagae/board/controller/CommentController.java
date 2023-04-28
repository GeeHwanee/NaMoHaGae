package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.BoardComment;
import kr.kro.namohagae.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/board/free")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<List<BoardComment>> commentData(@ModelAttribute BoardComment boardComment) {
        Integer saveResult = commentService.commentData(boardComment);
        if (saveResult != null) {
            List<BoardComment> commentList = commentService.commentList(boardComment.getBoardNo());
            return new ResponseEntity<>(commentList, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}