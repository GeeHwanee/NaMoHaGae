package kr.kro.namohagae.global.entity;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Alias("Notification")
public class Notification {
    private Integer notificationNo;
    private Integer memberNo;
    private String notificationContent;
    private String notificationLink;
    private Boolean notificationReadEnabled;
}
