package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
}
