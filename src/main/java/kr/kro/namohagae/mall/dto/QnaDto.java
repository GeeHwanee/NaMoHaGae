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
    public static class QnaList {
        private String qnaContent;
        private String memberNickname;
        private LocalDateTime qnaWriteDate;
        private String qnaAnswerContent;
        private LocalDateTime qnaAnswerWriteDate;
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
        private List<QnaList> qna;


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

    @Data
    public static class Read{
        private Integer qnaNo;
        private String productImage;
        private String productName;
        private Integer qnaWriter;
        private String memberNickname;
        private String memberProfile;
        private String qnaContent;
        private LocalDateTime qnaWriteDate;
    }


    @Data
    public static class Put{
        private Integer qnaNo;
        private Integer qnaWriter;
        private String qnaAnswerContent;

        public Qna toEntity(){
           return Qna.builder().qnaWriter(qnaWriter).qnaAnswerContent(qnaAnswerContent).qnaNo(qnaNo).qnaAnswerWriteDate(LocalDateTime.now()).build();
        }
    }
    @Data
    public static class FindByMemberNo{
        private String productImage;
        private Integer qnaNo;
        private String productName;
        private Integer checkAnswer;
        private LocalDateTime qnaWriteDate;
    }
    @Data
    @AllArgsConstructor
    public static class PaginationMyQna {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<QnaDto.FindByMemberNo> qna;
    }
    @Data
    public static class MyQnA{
        private String qnaContent;
        private String qnaAnswerContent;
        private LocalDateTime qnaAnswerWriteDate;
    }
}
