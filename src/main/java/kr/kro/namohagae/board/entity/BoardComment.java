package kr.kro.namohagae.board.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardComment {
    private Integer boardNo;
    private Integer commentNo;
    private Integer memberNo;
    private String commentContent;
    private LocalDateTime commentWriteDate;
    private Boolean commentEnabled;


}
