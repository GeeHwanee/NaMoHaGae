package kr.kro.namohagae.global.security;

import kr.kro.namohagae.global.util.constants.Roles;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(MemberDao memberDao, PasswordEncoder passwordEncoder )   {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")) {
            return new MyUserDetails(0, username, passwordEncoder.encode("1234"), true, List.of(new SimpleGrantedAuthority(Roles.ADMIN.getRole())));
        }
        try {
            Member member = memberDao.findByUsername(username).get();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(Roles.USER.getRole()));
            return new MyUserDetails(member.getMemberNo(), username, member.getMemberPassword(), member.getMemberEnabled(), authorities);

        }catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }


    }

}
