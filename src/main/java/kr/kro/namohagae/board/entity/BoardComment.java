package kr.kro.namohagae.board.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardComment {
    private Integer boardNo;
    private Integer memberNo;
    private Integer commentNo;
    private String commentContent;
    private LocalDate commentWriteDate;
    private Boolean commentEnabled;


}
