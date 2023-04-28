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

    // 페이지네이션
    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<Qna> qnas;
    }

    // 질문 쓰기
    @Data
    public static class Write {
        private Integer qnaNo;
        private Integer productNo;
        private String qnaWriter;
        private String qnaContent;
        private LocalDateTime qnaWriteDate;

        public void setQnaNo(Integer qnaNo) {
            this.qnaNo = qnaNo;
        }

        public Qna toEntity(String memberEmail) {
            return Qna.builder().productNo(productNo).qnaWriter(memberEmail).qnaContent(qnaContent).qnaWriteDate(LocalDateTime.now()).build();
        }
    }
}
