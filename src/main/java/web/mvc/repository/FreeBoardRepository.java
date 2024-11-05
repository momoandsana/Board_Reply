package web.mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import web.mvc.domain.FreeBoard;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long>
, QuerydslPredicateExecutor<FreeBoard> {

    @Query("select f from FreeBoard f left join f.repliesList")
    List<FreeBoard> join02();

    // fetch 를 사용해서 n+1 문제 해결. 1을 땡겨올 때 n도 같이 땡겨옴
    @Query("select f from FreeBoard f left join fetch f.repliesList")
     List<FreeBoard> join03();

    @Query(value = "select distinct f from FreeBoard f  left join fetch f.repliesList",
            countQuery = "select count(distinct f.bno) from FreeBoard f left join f.repliesList" )
     Page<FreeBoard> join04(Pageable page);
    /*
    이거 써야지 n+1 문제가 없음,
    return boardRepository.findAll(pageable).map(board -> modelMapper.map(board, FreeBoardDTO.class));
    이거는 N+1 문제가 있음
     */


}
/*
FreeBoard 필드에 기반해서 기본 함수들이 만들어진다
 */