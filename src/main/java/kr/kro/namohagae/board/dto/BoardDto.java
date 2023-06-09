package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
    @Data
    public static class Write {
        private Integer townNo;
        private Integer memberNo;
        private String boardTitle;
        private String boardContent;

        public Board toEntity(Integer townNo, Integer memberNo, String title, String content) {
            return Board.builder().townNo(townNo).memberNo(memberNo).boardTitle(title).boardContent(content).boardWriteDate(LocalDateTime.now()).build();
        }
    }

    @Data
    public static class Preview{
        private Integer boardNo;
        private Integer townNo;
        private String townDong;
        private Integer memberNo;
        private String memberNickname;
        private String boardTitle;
        private Integer commentCount;
        private LocalDateTime boardWriteDate;
        private Integer boardReadCount;
        private Integer boardRecommendCount;
    }

    @Data
    public static class Read{
        private Integer boardNo;
        private Integer townNo;
        private String townDong;
        private Integer memberNo;
        private String memberNickname;
        private String memberProfileImage;
        private String boardTitle;
        private String boardContent;
        private LocalDateTime boardWriteDate;
        private Integer boardReadCount;
        private Integer boardRecommendCount;
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
    public static class PaginationPreview {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<BoardDto.Preview> preview;
    }
    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<BoardDto.FindAllByMemberNo> board;
    }
}
