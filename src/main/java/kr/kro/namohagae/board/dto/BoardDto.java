package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

public class BoardDto {
    @Data
    public static class write {
        private Integer memberNo;
        private String title;
        private String content;

        public Board toEntity(Integer memberNo, String title, String content) {

            return Board.builder().memberNo(memberNo).boardTitle(title).boardContent(content).build();

        }
    }
    public static class list {
        private Integer boardNo;
        private String boardTitle;
        private LocalDateTime boardWriteDate;
        private Integer boardReadCount;
        private Integer boardRecommendCount;

        public Board toEntity(Integer boardNo, String boardTitle, LocalDateTime boardWriteDate, Integer boardReadCount, Integer boardRecommendCount) {

            return Board.builder().boardNo(boardNo).boardTitle(boardTitle).boardWriteDate((boardWriteDate)).boardReadCount(boardReadCount).boardRecommendCount(boardRecommendCount).build();

        }
    }
}