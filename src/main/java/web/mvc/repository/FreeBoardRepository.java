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
    쿼리 2개가 실행된다

    이거 써야지 n+1 문제가 없음,
    return boardRepository.findAll(pageable).map(board -> modelMapper.map(board, FreeBoardDTO.class));
    이거는 N+1 문제가 있음

    findAll 함수는 FreeBoard 엔티티에 repliesList 가  FetchType 이 lazy 로 되어 있으므로
    FreeBoard 리스트로 가지고 올 때는 로딩되지 않는다
    repliesList 가 필요해질 때마다(필요한 의도가 느껴질 때마다) repliesList 를 가지고 오는데
    이 때 1(게시글)+n(댓글의 개수) 만큼의 쿼리가 나가게 된다

    위에 있는 쿼리문으로 활용하면 fetch join 을 하기 때문에 FreeBoard 와 repliesList 를 한 번에 가기고 온다

     select distinct fb1_0.bno,fb1_0.content,fb1_0.insert_date,fb1_0.password,fb1_0.readnum,rl1_0.free_bno,
     rl1_0.rno,rl1_0.content,rl1_0.insert_date,
     fb1_0.subject,fb1_0.update_date,fb1_0.writer from free_board
     fb1_0 left join reply rl1_0 on fb1_0.bno=rl1_0.free_bno order by fb1_0.bno

     여기서 게시물과 댓글 모두 가지고 온다
     */


}
/*
FreeBoard 필드에 기반해서 기본 함수들이 만들어진다
 */