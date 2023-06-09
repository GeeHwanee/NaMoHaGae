package kr.kro.namohagae.global.security;

import kr.kro.namohagae.global.dao.TownDao;
import kr.kro.namohagae.global.util.constants.Roles;
import kr.kro.namohagae.member.dao.DogDao;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;
    private final TownDao townDao;
    private final DogDao dogDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")) {
            return new MyUserDetails(-1, "관리자",0,"", username, passwordEncoder.encode("1234"), true, List.of(new SimpleGrantedAuthority(Roles.ADMIN.getRole())));
        }
        try {
            Member member = memberDao.findByUsername(username).get();
            String townDong = townDao.findTownDongByTownNo(member.getTownNo());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(Roles.USER.getRole()));
            if (dogDao.checkDogCountByMemberNo(member.getMemberNo())!=0){
                authorities.add(new SimpleGrantedAuthority(Roles.DOG.getRole()));
            }
            return new MyUserDetails(member.getMemberNo(), member.getMemberNickname(),member.getTownNo(), townDong,username, member.getMemberPassword(), member.getMemberEnabled(), authorities);

        }catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }


    }
    public Authentication createNewAuthentication(Authentication currentAuth, String username) {
        UserDetails newPrincipal = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal, currentAuth.getCredentials(), newPrincipal.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }

}
