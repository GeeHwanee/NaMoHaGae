package kr.kro.namohagae.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
//@Alias("Notification")
public class Notification {
    private Integer notificationNo;
    private Integer memberNo;
    private String notificationContent;
    private String notificationLink;
    private Boolean notificationReadEnabled;
}
