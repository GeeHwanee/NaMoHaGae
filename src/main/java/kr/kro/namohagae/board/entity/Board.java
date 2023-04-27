package kr.kro.namohagae.board.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Board {

        private Integer boardNo;
        private Integer townNo;
        private Integer memberNo;
        private String boardTitle;
        private String boardContent;
        private LocalDate boardWriteDate;
        private Integer boardReadCount;
        private Integer boardRecommendCount;
        private Boolean boardEnabled;
        private String fileName;
        private String filePath;

    }

