package kr.kro.namohagae.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.kro.namohagae.global.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDto {
    @Data
    public static class Save{
        private Integer memberNo;
        private String reportCategory;
        private String reportContent;
        public Report toEntity(String reportContent,Integer reportMemberNo){
            return Report.builder().reportCategory(reportCategory).reportContent(reportContent).reportMemberNo(reportMemberNo).memberNo(memberNo).build();
        }
    }

    @Data
    public static class FindAll{
        private Integer reportNo;
        private Integer memberNo;
        private String reportCategory;
        private Integer reportMemberNo;
        private String reportContent;
        @JsonFormat(pattern = "yy년 MM월 dd일 hh:mm:ss")
        private LocalDateTime reportDate;
    }

    @Data
    @ToString
    @AllArgsConstructor
    public static class Pagination {
        private Integer pageno;
        private Integer prev;
        private Integer start;
        private Integer end;
        private Integer next;
        private List<ReportDto.FindAll> reports;
    }
}
