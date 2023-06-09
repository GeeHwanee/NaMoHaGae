package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.KnowledgeAnswer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KnowledgeAnswerDto {
    @Data
    public static class Write{
        private Integer knowledgeQuestionNo;
        private Integer questionMemberNo;
        private Integer answerMemberNo;
        private String knowledgeAnswerContent;

        public KnowledgeAnswer toEntity(Integer memberNo){
            return KnowledgeAnswer.builder().knowledgeQuestionNo(knowledgeQuestionNo).memberNo(memberNo).knowledgeAnswerContent(knowledgeAnswerContent).knowledgeAnswerWriteDate(LocalDateTime.now()).build();
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Read{
        private Integer knowledgeAnswerNo;
        private Integer memberNo;
        private String memberNickname;
        private String memberProfileImage;
        private String knowledgeAnswerContent;
        private LocalDateTime knowledgeAnswerWriteDate;
        private Boolean knowledgeAnswerSelectionEnabled;
        private Integer knowledgeAnswerRecommend;
    }

//    질문 제목, 질문 작성일자,답변 내용,채택시 채택됨, 작성일 표기
    @Data
    public static class myAnswerList{
        private Integer knowledgeQuestionNo;
        private String knowledgeQuestionTitle;
        private LocalDateTime knowledgeQuestionWriteDate;
        private Boolean knowledgeAnswerSelectionEnabled;
        private LocalDateTime knowledgeAnswerWriteDate;
        private String knowledgeAnswerContent;
    }
    @Data
    @AllArgsConstructor
    public static class myAnswerPagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private java.util.List<KnowledgeAnswerDto.myAnswerList> answers;
    }
}
