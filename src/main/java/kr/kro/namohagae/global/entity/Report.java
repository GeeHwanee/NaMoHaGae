package kr.kro.namohagae.global.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Report {
    private Integer reportNo;
    private Integer memberNo;
    private String reportCategory;
    private Integer reportMemberNo;
    private String reportContent;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime reportDate;
}
