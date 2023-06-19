package kr.kro.namohagae.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

//@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        String requestUri = request.getRequestURI();
       System.out.println(requestUri);
        if (isRestApiRequest(requestUri)) {
            // RESTful API 요청 처리
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied for RESTful API");
        } else {
            // MVC 이동 처리
            String errorMessage = "접근 권한이 없는 사용자 입니다.";
            request.getSession().setAttribute("errorMessage", errorMessage);


            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    private boolean isRestApiRequest(String requestUri) {
        // RESTful API 패턴을 확인하여 해당하는지 여부를 반환하는 로직
        // 예를 들어, "/api/"로 시작하는 패턴이 RESTful API라고 가정
        return requestUri.startsWith("/api/");
    }
}
