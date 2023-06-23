package kr.kro.namohagae.board.dto;

import kr.kro.namohagae.board.entity.KnowledgeQuestion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KnowledgeQuestionDto {
    @Data
    public static class Write{
        private String knowledgeQuestionTitle;
        private String knowledgeQuestionContent;
        private Integer knowledgeQuestionPoint;

        public KnowledgeQuestion toEntity(Integer memberNo){
            return KnowledgeQuestion.builder().memberNo(memberNo).knowledgeQuestionTitle(knowledgeQuestionTitle).knowledgeQuestionContent(knowledgeQuestionContent).knowledgeQuestionWriteDate(LocalDateTime.now()).knowledgeQuestionPoint(knowledgeQuestionPoint).build();
        }
    }
    @Data
    public static class Read{
        private Integer knowledgeQuestionNo;
        private Integer memberNo;
        private String memberNickname;
        private String memberProfileImage;
        private String knowledgeQuestionTitle;
        private String knowledgeQuestionContent;
        private Integer knowledgeQuestionReadCount;
        private LocalDateTime knowledgeQuestionWriteDate;
        private Integer knowledgeQuestionPoint;
    }

    @Data
    public static class Preview{
        private Integer knowledgeQuestionNo;
        private Integer memberNo;
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
        private List<KnowledgeQuestionDto.Preview> questions;
    }

    @Data
    public static class myQuestionList{
        private Integer knowledgeQuestionNo;
        private Integer answerCount;
        private String knowledgeQuestionTitle;
        private Integer knowledgeQuestionReadCount;
        private Boolean knowledgeAnswerSelectionEnabled;
        private LocalDateTime knowledgeQuestionWriteDate;
        private Integer knowledgeQuestionPoint;
    }
    @Data
    @AllArgsConstructor
    public static class myPagination {
        private Integer pageNo;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<myQuestionList> questions;
    }

}
