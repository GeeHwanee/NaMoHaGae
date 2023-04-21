package kr.kro.namohagae.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Alarm {
    private Integer alarmNo;
    private Integer memberNo;
    private String alarmContent;
    private String alarmLink;
    private Boolean alarmReadEnabled;
}
