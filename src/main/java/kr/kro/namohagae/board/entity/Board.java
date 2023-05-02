package kr.kro.namohagae.board.entity;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class Board {

        private Integer boardNo;
        private Integer townNo;
        private Integer memberNo;
        private String boardTitle;
        private String boardContent;
        private LocalDateTime boardWriteDate;
        private Integer boardReadCount;
        private Integer boardRecommendCount;
        private Boolean boardEnabled;


    }

