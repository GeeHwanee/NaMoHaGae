package kr.kro.namohagae.mall.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Qna {
    private Integer qnaNo;
    private Integer productNo;
    private String qnaWriter;
    private String qnaContent;
    private LocalDateTime qnaWriteDate;
    private String qnaAnswerContent;
    private LocalDateTime qnaAnswerWriteDate;
}
