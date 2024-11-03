package web.mvc.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;

@Aspect
@Component
public class SessionCheckAdvice {

    @Before("execution(* web.mvc.controller.*.*(..)) && " +
            "!execution(* web.mvc.controller.UserController.*(..)) && " +
            "!execution(* web.mvc.controller.HomeController.home(..))")
    public void checkSession()  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            throw new BasicException(ErrorCode.ACCESS_DENIED); // 예외 발생
        }
    }
}
