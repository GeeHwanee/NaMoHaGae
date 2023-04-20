package kr.kro.namohagae.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Dog {
    private Integer dogNo;
    private Integer memberNo;
    private LocalDateTime dogBirthdayDate;
    private String dogIntroduce;
    private Boolean dogGender;
    private Boolean dogNotGenderEnabled;
    private Integer dpgWeight;
    private String dogCategory;
    private String dogName;
    private String dogProfile;
}
