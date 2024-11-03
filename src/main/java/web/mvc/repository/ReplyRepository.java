package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
