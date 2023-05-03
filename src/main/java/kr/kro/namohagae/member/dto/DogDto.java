package kr.kro.namohagae.member.dto;

import kr.kro.namohagae.global.util.ImageConstants;
import kr.kro.namohagae.member.entity.Dog;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class DogDto {
    @Data
    public class List {
        private String dogProfileImage;
        private String dogName;
    }

    @Data
    public static class registeration{
        private String dogCategory;
        private String dogName;
        private String dogIntroduce;
        private MultipartFile dogProfile;
        private Double dogWeight;
        private Boolean dogGender;
        private Boolean dogNotGenderEnabled;
        private LocalDate dogBirthdayDate;

        public Dog toEntity(Integer memberNo,String dogProfile, String dogIntroduce){
            return  Dog.builder().dogCategory(dogCategory).dogGender(dogGender).dogIntroduce(dogIntroduce).dogName(dogName).
                    dogProfile(dogProfile).memberNo(memberNo).dogNotGenderEnabled(dogNotGenderEnabled).dogWeight(dogWeight).dogBirthdayDate(dogBirthdayDate).build();
        }
    }
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Read {
        private String dogProfile;
        private String dogName;
        private Boolean dogGender;
        private String dogCategory;
        private LocalDate dogBirthdayDate;
        private String dogNotGenderEnabled;
        private Double dogWeight;
        private String dogIntroduce;
    }
}
