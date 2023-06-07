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
        private String memberNickname;
        private Double memberGrade;
        private String memberImage;
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
