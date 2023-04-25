package kr.kro.namohagae.member.service;

import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MemberService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MemberDao memberDao;

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


}
