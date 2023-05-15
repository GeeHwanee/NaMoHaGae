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
        private String knowledgeAnswerContent;
        private LocalDateTime knowledgeAnswerWriteDate;
        private Boolean knowledgeAnswerSelectionEnabled;
        private Integer knowledgeAnswerRecommend;
    }

    @Data
    public static class List{
        private Integer knowledgeQuestionNo;
        private String memberNickname;
        private String knowledgeQuestionTitle;
        private Integer knowledgeQuestionReadCount;
        private LocalDateTime knowledgeQuestionWriteDate;
        private Integer knowledgeQuestionPoint;
    }

    @Data
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private java.util.List<KnowledgeAnswerDto.List> answers;
    }

}
