package kr.kro.namohagae.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MemberDao memberDao;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //Integer memberNo = memberDao.findNoByUsername(authentication.getName());
        //memberDao.resetMemberLoginCount(memberNo);
        String redirectUrl = "/";
        SavedRequest sr = new HttpSessionRequestCache().getRequest(request, response);
        HttpSession session = request.getSession();
        response.sendRedirect("/");


    }
}
