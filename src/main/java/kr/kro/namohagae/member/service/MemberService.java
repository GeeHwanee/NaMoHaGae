package kr.kro.namohagae.member.service;

import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.util.ImageConstants;
import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private DogDao dogDao;
    @Autowired
    private TownDao townDao;
    public void join(MemberDto.Join dto){
        String memberIntroduce = " ";
        String profileName = "default.jpg";
        if(dto.getMemberIntroduce()!=null){
            memberIntroduce = dto.getMemberIntroduce();
        }
        MultipartFile mf = dto.getMemberProfileImage();
        if(mf!=null && !mf.isEmpty()) {
            int postionOfDot = mf.getOriginalFilename().lastIndexOf(".");
            String ext = mf.getOriginalFilename().substring(postionOfDot);
            String currentDir = System.getProperty("user.dir")+"/";
            System.out.println(currentDir);
            String imagePath = currentDir+ImageConstants.IMAGE_PROFILE_FOLDER;
            File file = new File(imagePath, dto.getMemberEmail() + ext);
            try {
                mf.transferTo(file);
                profileName = dto.getMemberEmail() + ext;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        Integer townNo = townDao.findNoByDong(dto.getTownDong());
        String encodedPassword = passwordEncoder.encode(dto.getMemberPassword());
        Member member = dto.toEntity(encodedPassword, profileName, memberIntroduce,townNo);
        memberDao.save(member);
    }

    public MemberDto.Read read(Integer memberNo) {
        Member member = memberDao.findByMember(memberNo).get();
        return member.toReadDto();
    }


    public void resign(Integer memberNo) {
        try {
//            String profile = memberDao.findByMember(memberNo).get().getMemberProfileImage();
//            Integer positionOfEqual = profile.lastIndexOf("=");
//            String fileName = profile.substring(positionOfEqual+1);
//            File file = new File(ImageConstants.IMAGE_PROFILE_FOLDER, fileName);
//
//            // 예외가 발생하면 롤백되는데, 발생 안하니까... 어떻게 처리하지? -> 작업을 중단하자(아래 코드는 설명용)
//            if (file.exists()==false) {
//                return;
//            }
//            if (fileName.equals("default.jpeg")==false) {
//                file.delete();
//            }
            memberDao.memberEnabled(memberNo,false);
            System.out.println("123124");
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    public Boolean update(MultipartFile profile, String nickname, Integer memberNo,String password,String phone,Integer townNo) {


        if (profile==null || profile.isEmpty()==true) {
            System.out.println("11");
            memberDao.updateMember(memberNo,password,nickname,phone,townNo,null);
            return true;
        }else {    // else는 Don't care -> 신경쓰지 않는다
        }
        int postionOfDot = profile.getOriginalFilename().lastIndexOf(".");
        String ext = profile.getOriginalFilename().substring(postionOfDot);
        File file = new File(ImageConstants.IMAGE_PROFILE_FOLDER, memberNo + ext);
        try {
            profile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Boolean a = memberDao.updateMember(memberNo,password,nickname,phone,townNo,memberNo+ext);
        System.out.println("11");
        return a;

    }


    public Boolean checkNickanme(Integer memberNo, String nickname) {
        Member member = memberDao.findByMember(memberNo).get();
        Boolean resultDB = memberDao.existsByNickname(nickname)==1;                                // 기존 DB에 이메일이 있다면 false 리턴
        Boolean resultUser = !member.getMemberNickname().equals(nickname);
        System.out.println(nickname);
       if(resultDB==false){
           System.out.println("rhjf");
           return true;
       }
       if(resultUser==false){
           System.out.println("gkgk");
           return false;
       }
        return true;
    }



}
