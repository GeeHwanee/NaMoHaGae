package kr.kro.namohagae.board.controller;


import kr.kro.namohagae.board.entity.BoardComment;
import kr.kro.namohagae.board.service.CommentService;

import kr.kro.namohagae.global.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping("/comment/free/write")
    public ResponseEntity<?> write(BoardComment boardComment,Principal principal) {


        return ResponseEntity.ok(commentService.commentData(boardComment,principal.getName()));
    }

    @PostMapping("/comment/free/update")
    public ResponseEntity<?> update(BoardComment boardComment) {

            System.out.println("찎어보기" + boardComment);
         commentService.commentUpdate(boardComment);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/comment/free/delete")
    public ResponseEntity<?> delete(@RequestParam("commentNo") Integer commentNo) {

        System.out.println("댓글번호" + commentNo);
        commentService.commentDelete(commentNo);
        return ResponseEntity.ok(null);
    }
    public ResponseEntity<?> commentList(Integer pageno,@AuthenticationPrincipal MyUserDetails myUserDetails){
        Integer memberNo= myUserDetails.getMemberNo();
      return ResponseEntity.ok(commentService.myCommentList(pageno,memberNo));
    }

}