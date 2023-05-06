package kr.kro.namohagae.mall.dto;

import kr.kro.namohagae.mall.entity.Qna;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class QnaDto {

    @Data
    public static class list {
        // 내용 작성자 작성일, 답변내용 답변일자
        private String qnaContent;
        private Integer qnaWriter;
        private String qnaAnswerContent;
    }
    
    // 페이지네이션
    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private Integer productNo;
        private List<QnaDto.list> qna;
    }

    // 질문 쓰기
    @Data
    public static class Write {
        private Integer productNo;
        private Integer qnaWriter;
        private String qnaContent;

        public Qna toEntity(Integer memberNo) {
            return Qna.builder().productNo(productNo).qnaWriter(memberNo).qnaContent(qnaContent).qnaWriteDate(LocalDateTime.now()).build();
        }
    }
}
