package kr.kro.namohagae.global.dto;

import lombok.*;

import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class NotificationDto {
    @Data
    public static class FindAll {
        private Integer notificationNo;
        private String notificationContent;
        private String notificationRink;
        private Boolean notificationReadEnabled;
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
        private List<FindAll> notifications;
    }




}
