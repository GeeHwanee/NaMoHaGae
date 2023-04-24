package kr.kro.namohagae.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@AllArgsConstructor
@Builder
@Alias("Alarm")
public class Alarm {
    private Integer alarmNo;
    private Integer memberNo;
    private String alarmContent;
    private String alarmLink;
    private Boolean alarmReadEnabled;
}
