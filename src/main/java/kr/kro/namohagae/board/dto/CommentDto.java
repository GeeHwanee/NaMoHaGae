package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.mall.dto.QnaDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {
    @Data
    public static class CommentList{
        private Integer memberNo;
        private Integer boardNo;
        private Integer commentNo;
        private String memberNickname;
        private String commentContent;
        private LocalDateTime commentWriteDate;
        private Boolean commentEnabled;

    }
    @Data
    public static class MyCommentList{
        private String boardTitle;
        private Integer townNo;
        private Integer boardNo;
        private String commentContent;
        private LocalDateTime commentWriteDate;
    }
    @Data
    @AllArgsConstructor
    public static class PaginationMyCommentList {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<CommentDto.MyCommentList> commentList;
    }
}
