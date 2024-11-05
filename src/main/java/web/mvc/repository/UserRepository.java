package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import web.mvc.domain.User;

public interface UserRepository extends JpaRepository<User,String>,
        QuerydslPredicateExecutor<User> {
    User findByUserId(String userId);
}
