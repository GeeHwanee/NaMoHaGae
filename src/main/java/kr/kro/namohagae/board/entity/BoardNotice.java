package kr.kro.namohagae.board.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardNotice {
    private Integer boardNoticeNo;
    private String boardNoticeWriter;
    private String boardNoticeTitle;
    private String boardNoticeContent;
    private LocalDateTime boardNoticeWriteDate;
    private Integer boardNoticeReadCount;

}
