package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.Board;
import lombok.Data;

public class BoardTownDto {

    @Data
    public static class write {
        private Integer townNo;
        private Integer memberNo;
        private String title;
        private String content;

        public Board toEntity(Integer townNo,Integer memberNo, String title, String content) {

            return Board.builder().townNo(townNo).memberNo(memberNo).boardTitle(title).boardContent(content).build();

        }
    }
}
