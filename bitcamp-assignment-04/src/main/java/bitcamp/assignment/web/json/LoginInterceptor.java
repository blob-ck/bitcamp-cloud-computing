package bitcamp.assignment.web.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        handler = session.getAttribute("loginUser");
        if (handler == null) {
            System.out.println("로그인 먼저 해라");
            return false;
        }
        System.out.println("로그인 세션체크 OK~");
        return true;
    }
}
