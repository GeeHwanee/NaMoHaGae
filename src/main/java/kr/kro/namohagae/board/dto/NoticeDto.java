package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.BoardNotice;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class NoticeDto {
    @Data
    public static class Add{
        private String boardNoticeTitle;
        private String boardNoticeContent;

        public BoardNotice toEntity(){
            return BoardNotice.builder().boardNoticeWriter("관리자").boardNoticeTitle(boardNoticeTitle).boardNoticeContent(boardNoticeContent).boardNoticeWriteDate(LocalDateTime.now()).build();
        }
    }


}
