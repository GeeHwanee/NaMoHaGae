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
        Dog dog = dogDao.findByDog(dogNo).get();
        String dogNotGenderEnbled = "";
        if(dog.getDogNotGenderEnabled()==true){
            dogNotGenderEnbled = "완료";
        }else {
            dogNotGenderEnbled="안함";
        }
        return dog.toReadDto(dogNotGenderEnbled);
    }

    public boolean checkNickanme(Integer memberNo, String nickname) {
        return true;
    }

    public Boolean update(MultipartFile profile, String nickname, Integer memberNo, String password, String phone, Integer townNo) {
        return true;
    }

    public void resign(Integer dogNo) {
        try {
            String profile = dogDao.findByDog(dogNo).get().getDogProfile();
            Integer positionOfEqual = profile.lastIndexOf("=");
            String fileName = profile.substring(positionOfEqual+1);
            File file = new File(ImageConstants.IMAGE_DOG_FOLDER, fileName);

            // 예외가 발생하면 롤백되는데, 발생 안하니까... 어떻게 처리하지? -> 작업을 중단하자(아래 코드는 설명용)
            if (file.exists()==false) {
                return;
            }
            if (fileName.equals("default.jpg")==false) {
                file.delete();
            }
            dogDao.delete(dogNo);
            System.out.println("123124");
        }catch(NoSuchElementException e){
            throw e;
        }
    }
}
