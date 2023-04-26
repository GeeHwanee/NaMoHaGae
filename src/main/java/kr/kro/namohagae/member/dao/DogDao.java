package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.entity.Dog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DogDao {
    public Integer register(Dog dog);

    public Integer delete(Integer memberNo,Integer dogNo);

    public Integer update(Integer memberNo,Integer dogNo,String introduce,Boolean notGenderEnabled,Double weight,String name,String profile);

    public List<DogDto.List> findDogList(Integer memberNo);


    public Optional<Dog> findByDog(Integer memberNo,Integer dogNo);
}
