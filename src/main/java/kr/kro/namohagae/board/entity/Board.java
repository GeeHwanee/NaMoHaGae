package kr.kro.namohagae.board.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Data
public class Board {

    private Integer boardNo;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardWriteDate;
    private Integer boardReadCount;
    private Integer boardRecommendCount;
    private Boolean boardEnabled;
}
