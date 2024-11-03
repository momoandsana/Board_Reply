package web.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class UserController {
    @GetMapping("/user/login")
    public String user() {
        return "user/login";
    }
}

//@Controller
//@Slf4j
//public class HomeController {
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
//}
