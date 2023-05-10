package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.Board;
import kr.kro.namohagae.member.dto.FollowDto;
import kr.kro.namohagae.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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

    @Data
    public static class FindAllByMemberNo {
        private Integer boardNo;
        private Integer townNo;
        private String boardTitle;
        private Integer boardReadCount;
        private Integer boardRecommendCount;
        private Integer commentCount;
        private LocalDateTime boardWriteDate;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<BoardDto.FindAllByMemberNo> board;
    }
}
