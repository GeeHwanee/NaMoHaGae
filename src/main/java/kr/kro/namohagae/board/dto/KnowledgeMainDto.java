package kr.kro.namohagae.board.dto;

import lombok.Data;

@Data
public class KnowledgeMainDto {
    private Integer KnowledgeQuestionNo;
    private String KnowledgeQuestionTitle;
    private String KnowledgeQuestionContent;
    private Integer KnowledgeQuestionReadCount;
    private Integer answerCount;

}
