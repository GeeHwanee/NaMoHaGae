package kr.kro.namohagae.member.service;

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
    PasswordEncoder passwordEncoder;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private DogDao dogDao;

    public void join(MemberDto.Join dto){
            String memberIntroduce = " ";
        if(dto.getMemberIntroduce()!=null){
            memberIntroduce = dto.getMemberIntroduce();
        }
        MultipartFile mf = dto.getMemberProfileImage();
        String profileName = "default.jpg";
        /*if(mf!=null && mf.isEmpty()==false) {

            int postionOfDot = mf.getOriginalFilename().lastIndexOf(".");
            String ext = mf.getOriginalFilename().substring(postionOfDot);
            File file = new File(, dto.getUsername() + ext);
            try {
                mf.transferTo(file);
                profileName = dto.getUsername() + ext;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }*/
        String encodedPassword = passwordEncoder.encode(dto.getMemberPassword());
        Member member = dto.toEntity(encodedPassword, profileName, memberIntroduce);
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
//            File file = new File(Constants.PROFILE_FOLDER, fileName);
//
//            // 예외가 발생하면 롤백되는데, 발생 안하니까... 어떻게 처리하지? -> 작업을 중단하자(아래 코드는 설명용)
//            if (file.exists()==false) {
//                return;
//            }
//            if (fileName.equals("default.jpeg")==false) {
//                file.delete();
//            }
            memberDao.memberEnabled(memberNo,false);
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    public Boolean update(MultipartFile profile, String email, Integer memberNo, String loginId) {


        if (profile==null || profile.isEmpty()==true) {
            memberDao.update(null, email, loginId);
            return true;
        }else {	// else는 Don't care -> 신경쓰지 않는다
        }
        int postionOfDot = profile.getOriginalFilename().lastIndexOf(".");
        String ext = profile.getOriginalFilename().substring(postionOfDot);
        File file = new File(Constants.PROFILE_FOLDER, memberNo + ext);
        try {
            profile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return memberDao.update(loginId+ext, email, memberNo);
    }
}
