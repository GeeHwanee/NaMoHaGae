package kr.kro.namohagae.board.controller;

import kr.kro.namohagae.board.entity.BoardComment;
import kr.kro.namohagae.board.service.CommentService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/board/free")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment/write")
    public ResponseEntity<List<BoardComment>> commentData(@ModelAttribute BoardComment boardComment) {
        Integer saveResult = commentService.commentData(boardComment);
        if (saveResult != null) {
            List<BoardComment> commentList = commentService.commentList(boardComment.getBoardNo());

            return ResponseEntity.ok(commentList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<Void> delete(Integer boardNo) {
        commentService.commentDelete(boardNo);

        return ResponseEntity.ok(null);
    }

}