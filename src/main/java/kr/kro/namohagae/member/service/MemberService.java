package kr.kro.namohagae.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.kro.namohagae.global.dao.BlockDao;
import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.util.constants.ImageConstants;
import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.dto.MemberDto;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberDao memberDao;
    private final DogDao dogDao;
    private final TownDao townDao;
    private final BlockDao blockDao;
    private final JavaMailSender javaMailSender;
    String authenticationCode = "";
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

            File file = new File(ImageConstants.IMAGE_PROFILE_DIRECTORY, dto.getMemberEmail() + ext);
            try {
                mf.transferTo(file);
                profileName = dto.getMemberEmail() + ext;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        Integer townNo = townDao.findNoByDong(dto.getTownDong());
        String encodedPassword = passwordEncoder.encode(dto.getMemberPassword());
        Boolean checkKaKao = dto.getMemberCheckKaKao();
        Member member = dto.toEntity(encodedPassword, profileName, memberIntroduce,townNo,checkKaKao);
        memberDao.save(member);
    }
    private void sendMail(String from, String to, String title, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            // false면 글자로 날아가고, true면 html로 날아간다
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public MemberDto.Read read(Integer memberNo) {
        Member member = memberDao.findByMemberNo(memberNo).get();
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
        }catch(NoSuchElementException e){
            throw e;
        }
    }

    public Boolean update(MemberDto.UpdateMember dto,Integer memberNo) {
        String newPassword = "";
        Integer townNo = null;
        Double latitude =null;
        Double longitude = null;
        String newNickname="";
        if(dto.getLatitude()!=null){
        String strLatitude= dto.getLatitude().substring(0,11);
        latitude = Double.parseDouble(strLatitude);
        }
        if (!dto.getNickname().equals("")){
            if (checkUpdateNickanme(memberNo,dto.getNickname())){
                newNickname=dto.getNickname();
            }
        }
        if(dto.getLongitude()!=null){
        String strLongitude= dto.getLongitude().substring(0,11);
        longitude = Double.parseDouble(strLongitude);}
        if (!dto.getPassword().equals("")){
            newPassword=passwordEncoder.encode(dto.getPassword());
        }
        if(dto.getTownDong()!=null) {
            townNo = townDao.findNoByDong(dto.getTownDong());
        }
        if (dto.getProfile()==null || dto.getProfile().isEmpty()==true) {
            memberDao.updateMember(memberNo,newPassword,newNickname,dto.getPhone(),townNo,null,longitude,latitude, dto.getIntroduce());
            return true;
        }else {    // else는 Don't care -> 신경쓰지 않는다
        }
        int postionOfDot = dto.getProfile().getOriginalFilename().lastIndexOf(".");
        String ext = dto.getProfile().getOriginalFilename().substring(postionOfDot);
        System.out.println(ext+"afdfbdfb");
        if(dto.getNickname().equals("")){
            Member member= memberDao.findByMemberNo(memberNo).get();
            newNickname=member.getMemberNickname();
        }
        File file = new File(ImageConstants.IMAGE_PROFILE_DIRECTORY, newNickname + ext);
        try {
            dto.getProfile().transferTo(file);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Boolean a = memberDao.updateMember(memberNo,newPassword,newNickname,dto.getPhone(),townNo, newNickname+ext,longitude,latitude, dto.getIntroduce());
        return a;

    }


    public Boolean checkUpdateNickanme(Integer memberNo, String nickname) {
        Member member = memberDao.findByMemberNo(memberNo).get();
        Boolean resultDB = memberDao.existsByNickname(nickname);                                // 기존 DB에 이메일이 있다면 false 리턴
        Boolean resultUser = member.getMemberNickname().equals(nickname);
        System.out.println(nickname);
       if(resultDB==false){
           return true;
       }
       if(resultUser==false){
           return false;
       }
        return true;
    }

    public Boolean sendAuthenticationCode(String email) {
        try {
                authenticationCode  = RandomStringUtils.random(5, true, true).toUpperCase();
            sendMail("aaa@aa.com", email, "인증코드", authenticationCode);
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }
    public Boolean checkAuthenticationCode(String code){
          return  code.equals(authenticationCode);

    }
    public void mailTest() {
        sendMail("admin@zmall.com","dlswh6289@gmail.com","안녕하세요","이메일입니다");
    }

    public Boolean checkEmail(String email){
     return memberDao.existsByEmail(email);
    }
    public Boolean checkNickname(String nickname){
        return memberDao.existsByNickname(nickname);
    }

    public MemberDto.Join kakaoJoin(String kakaoEmail,String kakaoName){
        return MemberDto.Join.builder().memberPassword(kakaoEmail).memberEmail(kakaoEmail).memberNickname(kakaoName).memberCheckKaKao(true).build();
    }


    public String findEmailByNicknameAndPhone(String nickname, String phone) {
        return memberDao.findEmailByNicknameAndPhone(nickname,phone);
    }

    public Boolean changePassword(String memberPassword,String memberEmail) {
        Integer memberNo=memberDao.findNoByUsername(memberEmail);
        if (blockDao.checkByMemberNo(memberNo)==false){
        memberDao.resetMemberLoginCount(memberNo);
        memberDao.memberEnabled(memberNo,true);
        System.out.println("fdge");
        }else {
            System.out.println("fdfbdb");
            return false;
        }
        String password=passwordEncoder.encode(memberPassword);
        return memberDao.changePassword(password,memberEmail);
    }

    public Integer findMemberPointByMemberNo(Integer memberNo) {
        return memberDao.findMemberPointByMemberNo(memberNo);
    }

    public List<MemberDto.Preview> preview(String searchName) {
        return memberDao.preview(ImageConstants.IMAGE_PROFILE_URL, searchName);
    }

    @Transactional
    public Boolean updateMemberEnabled(Integer memberNo) {
        memberDao.updateMemberEnabled(memberNo);
        memberDao.resetMemberLoginCount(memberNo);
       return memberDao.findMemberEnabledByMemberNo(memberNo);
    }
}

