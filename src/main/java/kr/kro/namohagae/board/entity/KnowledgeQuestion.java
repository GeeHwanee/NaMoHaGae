package kr.kro.namohagae.board.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KnowledgeQuestion {
    private Integer knowledgeQuestionNo;
    private Integer memberNo;
    private String knowledgeQuestionTitle;
    private String knowledgeQuestionContent;
    private Integer knowledgeQuestionReadCount;
    private LocalDateTime knowledgeQuestionWriteDate;
    private Integer knowledgeQuestionPoint;
}
