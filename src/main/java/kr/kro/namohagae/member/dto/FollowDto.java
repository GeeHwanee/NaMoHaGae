package kr.kro.namohagae.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

public class FollowDto {
    @Data
    public static class list{
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
        private List<FollowDto.list> follow;
    }

}
