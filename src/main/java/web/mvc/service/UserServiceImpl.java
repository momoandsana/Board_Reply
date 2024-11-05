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

        // findUserId 는 Optional<User> 로 반환해야 한다
        User dbUser = userRepository
                .findByUserId(user.getUserId())
                .orElseThrow(()->new BasicException(ErrorCode.NOTFOUND_ID));

//        if (dbUser == null) {// 아이디가 없는 경우
//            throw new BasicException(ErrorCode.NOTFOUND_ID);
//        }

        if (!dbUser.getPwd().equals(user.getPwd())) {// 비밀번호가 틀린 경우
            throw new BasicException(ErrorCode.WRONG_PASS);
        }
        return dbUser;
    }
}

