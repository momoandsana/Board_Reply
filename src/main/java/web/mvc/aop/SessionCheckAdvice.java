package web.mvc.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SessionCheckAdvice {

    @Before("execution(* web.mvc.controller.*.*(..))")
    public void checkSession()  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            //throw new AuthenticationException("로그인이 필요합니다."); // 예외 발생
        }
    }
}
