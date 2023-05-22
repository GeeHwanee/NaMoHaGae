package kr.kro.namohagae.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardMainList {
    private Integer boardNo;
    private String boardTitle;
    private LocalDateTime boardWriteDate;
    private String townDong;
    private Integer commentCount;
}
