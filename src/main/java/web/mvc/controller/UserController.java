package web.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.mvc.domain.User;
import web.mvc.service.UserService;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {
    /*
    세션 관리는 컨트롤러에서
     */
    private final UserService userService;

    @GetMapping("/user/login")
    public String user() {
        return "user/login";
    }

    @PostMapping("/user/loginCheck")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");

        User user = new User(userId, pwd, null);

        User dbUser = userService.loginCheck(user);
        if(dbUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", dbUser);
            return "redirect:/";
        }


        return "redirect:/user/login";
    }


    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request)
    {
        HttpSession session=request.getSession();
        if(session!=null)
        {
            session.invalidate();
        }
        return "redirect:/";// 로그웃하면 메인피에지로
    }

}


