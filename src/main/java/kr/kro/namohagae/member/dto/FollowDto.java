package kr.kro.namohagae.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

public class FollowDto {
    @Data
    public static class FollowList {
        private Integer followMemberNo;
        @JsonFormat(pattern = "yy년 MM월 dd일 hh:mm:ss")
        private LocalDateTime puchingDate;
        private String memberNickname;
        private Double memberGrade;
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
        private java.util.List<FollowList> follow;
    }

}
