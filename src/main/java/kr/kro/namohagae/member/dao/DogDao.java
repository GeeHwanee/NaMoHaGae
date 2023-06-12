package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.entity.Dog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DogDao {
    public Integer register(Dog dog);

    public Integer delete(Integer dogNo);

    public Boolean update(Integer dogNo,String introduce,Boolean notGenderEnabled,Double weight,String name,String profile);

    public List<DogDto.DogList> findDogList(String url, Integer memberNo);


    public Optional<Dog> findByDog(Integer dogNo);

    public Integer checkDogCountByMemberNo(Integer memeberNo);
    public Boolean checkMaster(Integer memberNo,Integer dogNo);
}
