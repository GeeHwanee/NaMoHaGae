package kr.kro.namohagae.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardTownListDto {

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

