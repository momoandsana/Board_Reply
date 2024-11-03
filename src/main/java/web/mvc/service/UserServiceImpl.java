package web.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.domain.User;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loginCheck(User user)  {
        User dbUser = userRepository.findByUserId(user.getUserId());

        if (dbUser == null) {
            throw new BasicException(ErrorCode.NOTFOUND_ID);
        }

        if (!dbUser.getPwd().equals(user.getPwd())) {
            throw new BasicException(ErrorCode.WRONG_PASS);
        }
        return dbUser;
    }
}

