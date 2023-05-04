package kr.kro.namohagae.member.service;

import kr.kro.namohagae.global.util.ImageConstants;
import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.dto.DogDto;
import kr.kro.namohagae.member.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DogService {
    @Autowired
    private DogDao dogDao;
    public void save(DogDto.registeration dto, Integer memberNo) {
        String memberIntroduce = " ";
        String profileName = "default.jpg";
        if(dto.getDogIntroduce()!=null){
            memberIntroduce = dto.getDogIntroduce();
        }
        MultipartFile mf = dto.getDogProfile();
        if(mf!=null && !mf.isEmpty()) {
            int postionOfDot = mf.getOriginalFilename().lastIndexOf(".");
            String ext = mf.getOriginalFilename().substring(postionOfDot);
            String currentDir = System.getProperty("user.dir")+"/";
            System.out.println(currentDir);
            String imagePath = currentDir+ ImageConstants.IMAGE_DOG_FOLDER;
            File file = new File(imagePath, dto.getDogName() + ext);
            try {
                mf.transferTo(file);
                profileName = dto.getDogName() + ext;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        Dog dog = dto.toEntity(memberNo, profileName, memberIntroduce);
        dogDao.register(dog);
    }

    public DogDto.Read read(Integer dogNo) {
        System.out.println(dogNo);
        Dog dog = dogDao.findByDog(dogNo).get();
        String dogNotGenderEnbled = "";
        if(dog.getDogNotGenderEnabled()==true){
            dogNotGenderEnbled = "완료";
        }else {
            dogNotGenderEnbled="안함";
        }
        return dog.toReadDto(dogNotGenderEnbled);
    }

    public Boolean update(MultipartFile profile, String name,String introduce, Boolean notGenderEnabled,Double weight,Integer dogNo) {
        File file = null;
        String ext = "";
        String currentDir = System.getProperty("user.dir")+"/";
        if (profile==null || profile.isEmpty()==true) {
            dogDao.update(dogNo,introduce,notGenderEnabled,weight,name,null);
            return true;
        }else {    // else는 Don't care -> 신경쓰지 않는다
        }
        if(name!=null||name.trim().equals("")==false){
        int postionOfDot = profile.getOriginalFilename().lastIndexOf(".");
        ext = profile.getOriginalFilename().substring(postionOfDot);
        file = new File(currentDir+ImageConstants.IMAGE_DOG_FOLDER, name + ext);}else{}
        try {
            profile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Boolean a = dogDao.update(dogNo,introduce,notGenderEnabled,weight,name,name+ext);
        return a;
    }

    public void resign(Integer dogNo) {
        try {
            String currentDir = System.getProperty("user.dir")+"/";
            String profile = dogDao.findByDog(dogNo).get().getDogProfile();
            File file = new File(currentDir+ImageConstants.IMAGE_DOG_FOLDER, profile);

            // 예외가 발생하면 롤백되는데, 발생 안하니까... 어떻게 처리하지? -> 작업을 중단하자(아래 코드는 설명용)
            if (file.exists()==false) {
                return;
            }
            if (profile.equals("default.jpg")==false) {
                file.delete();
            }
            dogDao.delete(dogNo);
            System.out.println("123124");
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    public List<DogDto.dogList> dogList(Integer memberNo) {
        System.out.println("9807");
        List<DogDto.dogList> dogList = dogDao.findDogList(memberNo);
        dogList.get(0).setDogProfile(ImageConstants.IMAGE_DOG_URL+dogList.get(0).getDogProfile());
        dogList.get(1).setDogProfile(ImageConstants.IMAGE_DOG_URL+dogList.get(1).getDogProfile());
        return dogList;
    }
}
