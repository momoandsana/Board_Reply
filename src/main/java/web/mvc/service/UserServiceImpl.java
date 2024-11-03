package web.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.domain.User;
import web.mvc.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loginCheck(User user) throws Exception {
        User dbUser=userRepository.findByUserId(user.getUserId());

        if(dbUser !=null && dbUser.getPwd().equals(user.getPwd())) {
            return dbUser;
        }

        throw new Exception("로그인 에러");
    }
}

