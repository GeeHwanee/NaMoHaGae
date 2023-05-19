package kr.kro.namohagae.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.global.dao.BlockDao;
import kr.kro.namohagae.member.dao.MemberDao;
import kr.kro.namohagae.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private BlockDao blockDao;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Integer memberNo=0;
        if (exception instanceof BadCredentialsException){
            String username = request.getParameter("username");

            Member member = memberDao.findByUsername(username).get();
            memberNo=member.getMemberNo();
            if (member.getMemberLoginCount()<4){
                memberDao.increaseMemberLoginCount(member.getMemberNo());
                String msg = "로그인에 " + member.getMemberLoginCount() + "회 실패했습니다, 5회 이상 실패 시 계정이 비활성화 됩니다.";
                session.setAttribute("msg", msg);

            }else {
                memberDao.increaseMemberLoginCount(member.getMemberNo());
                memberDao.disabled(member.getMemberNo());
            }

        } else if (exception instanceof DisabledException) {
            if (blockDao.checkByMemberNo(memberNo)==true){
            String formattedDateTime =blockDao.findDeadlineDateByMemberNo(memberNo).format(formatter);
                String msg = "이 계정은 "+formattedDateTime+"까지 계정이 정지당했습니다";
                session.setAttribute("msg",msg);
            }
            session.setAttribute("msg", "비활성화된 계정입니다. 관리자에게 문의 하세요");

        }
        System.out.println("asdasd");
        System.out.println(session.getAttribute("msg"));
        response.sendRedirect("/login");
    }

}
