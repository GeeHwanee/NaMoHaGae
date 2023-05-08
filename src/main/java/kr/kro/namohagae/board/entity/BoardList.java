package kr.kro.namohagae.board.entity;

import kr.kro.namohagae.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardList {
    private Integer boardNo;
    private Integer townNo;
    private Integer memberNo;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardWriteDate;
    private Integer boardReadCount;
    private Integer boardRecommendCount;
    private Boolean boardEnabled;
    private String memberNickname;
}
