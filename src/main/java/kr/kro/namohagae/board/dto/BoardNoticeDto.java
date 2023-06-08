package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.BoardNotice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class BoardNoticeDto {
    @Data
    public static class Add{
        private String boardNoticeTitle;
        private String boardNoticeContent;

        public BoardNotice toEntity(){
            return BoardNotice.builder().boardNoticeWriter("관리자").boardNoticeTitle(boardNoticeTitle).boardNoticeContent(boardNoticeContent).boardNoticeWriteDate(LocalDateTime.now()).build();
        }
    }
    @Data
    public static class Update{
        private Integer boardNoticeNo;
        private String boardNoticeTitle;
        private String boardNoticeContent;

        public BoardNotice toEntity(){
            return BoardNotice.builder().boardNoticeNo(boardNoticeNo).boardNoticeTitle(boardNoticeTitle).boardNoticeContent(boardNoticeContent).build();
        }
    }

    @Data
    public static class Preview{
        private Integer boardNoticeNo;
        private String boardNoticeWriter;
        private String boardNoticeTitle;
        private LocalDateTime boardNoticeWriteDate;
        private Integer boardNoticeReadCount;
    }
    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<BoardNoticeDto.Preview> notice;
    }

    @Data
    public static class Read{
        private Integer boardNoticeNo;
        private String boardNoticeWriter;
        private String boardNoticeTitle;
        private String boardNoticeContent;
        private LocalDateTime boardNoticeWriteDate;
        private Integer boardNoticeReadCount;
    }

}
