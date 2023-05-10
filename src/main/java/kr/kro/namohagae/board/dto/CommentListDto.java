package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.board.entity.BoardComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentListDto {
        private Integer memberNo;
        private Integer boardNo;
        private Integer commentNo;
        private String memberNickname;
        private String commentContent;
        private LocalDateTime commentWriteDate;
        private Boolean commentEnabled;

}
