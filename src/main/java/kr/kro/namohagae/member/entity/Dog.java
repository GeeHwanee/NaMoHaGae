package kr.kro.namohagae.member.entity;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dto.DogDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Alias("Dog")
public class Dog {
    private Integer dogNo;
    private Integer memberNo;
    private LocalDate dogBirthdayDate;
    private String dogIntroduce;
    private Boolean dogGender;
    private Boolean dogNotGenderEnabled;
    private Double dogWeight;
    private String dogCategory;
    private String dogName;
    private String dogProfile;

    public DogDto.Read toReadDto(String dogNotGenderEnabled) {

        return new DogDto.Read(ImageConstants.IMAGE_DOG_URL +dogProfile,dogName,dogGender,dogCategory,dogBirthdayDate,dogNotGenderEnabled,dogWeight,dogIntroduce);
    }
}
