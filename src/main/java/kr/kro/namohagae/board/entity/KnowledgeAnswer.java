package kr.kro.namohagae.board.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KnowledgeAnswer {
    private Integer knowledgeQuestionNo;
    private Integer knowledgeAnswerNo;
    private Integer memberNo;
    private String knowledgeAnswerContent;
    private LocalDateTime knowledgeAnswerWriteDate;
    private Boolean knowledgeAnswerSelectionEnabled;

}
