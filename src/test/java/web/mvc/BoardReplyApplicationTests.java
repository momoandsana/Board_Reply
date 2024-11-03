package web.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.User;
import web.mvc.repository.UserRepository;

@SpringBootTest
class BoardReplyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Rollback(false)
    public void userInsert(){
        // User 엔티티 코드에 @Builder 추가하기
        userRepository.save(User.builder().userId("chan").pwd("1234").name("이창범").build());
        userRepository.save(User.builder().userId("jang").pwd("1234").name("장희정").build());
        userRepository.save(User.builder().userId("lee").pwd("1234").name("이가현").build());
    }

}
