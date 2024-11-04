package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.FreeBoard;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
}
/*
FreeBoard 필드에 기반해서 기본 함수들이 만들어진다
 */