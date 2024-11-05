package web.mvc;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.QFreeBoard;
import web.mvc.domain.QReply;
import web.mvc.repository.FreeBoardRepository;

import java.util.List;

@SpringBootTest
@Transactional
//@Commit
public class FreeBoardJoinFetchTests {

    @Autowired
    private FreeBoardRepository freeRep;

    @Autowired
    private JPAQueryFactory jpaFactory;

    /**
     * FreeBoard에서 Reply의 관계는  @OneToMany이므로 FetchType.LAZY(지연로딩) 이다.
     * 먼저, FreeBoard 검색을 한 다음,
     * b.getReplyList().size() 요청으로 인해
     * 각 부모글의 글의 개수만큼 reply테이블의 select을 실행한다 - 성능이슈(많은 select요청)
     *  :해결 방인
     *     join fetch 사용
     * */
    @Test
    void join01() {
        List<FreeBoard> list=freeRep.findAll(); // FreeBoard 레코드 수 만큼 reply select (1 + N)
        System.out.println("list.size = " + list.size());
        System.out.println("댓글....");
        list.forEach(b->System.out.println(b.getBno() +" = " + b.getRepliesList().size()));
    }


    /**
     * JPQL을 이용해서 join을 해본다.
     *   @Query("select f from FreeBoard f left join  f.repliesList")
     *   List<FreeBoard> join02();
     *
     *  결과는 join01() 단위 테스트와 동일하다(성능이슈)
     *  fetch 안 하고 그냥 join 하는 경우->똑같이 1+n 이슈가 발생함
     * */
    @Test
    void join02() {
        List<FreeBoard> list=freeRep.join02(); // FreeBoard 레코드 수 만큼 reply select (1 + N)
        System.out.println("list.size = " + list.size());
        System.out.println("댓글....");
        list.forEach(b->System.out.println(b.getBno() +" = " + b.getRepliesList().size()));

    }


    /**
     * JPQL join fetch 를 해본다.
     *    @Query("select f from FreeBoard f left join fetch f.repliesList")
     *     List<FreeBoard> join03();
     *     fetch 하므로 n+1 문제가 발생하지 않고 한 번에 다 가지고 온다
     *
     * */
    @Test
    void join03() {
        List<FreeBoard> list = freeRep.join03();
        System.out.println("list.size = " + list.size());
        System.out.println("----------------------------");
        // list.forEach(b->System.out.println(b.getBno() +" = " + b.getRepliesList().size()));

        list.forEach(b->{
            System.out.println(b.getBno()+" | " + b.getSubject());
            b.getRepliesList().forEach(r->{
                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
            });
            System.out.println();
        });
    }

    /**
     * JPQL문법을 이용하여
     * join fetch + 페이징처리 쿼리
     *     @Query(value = "select distinct f from FreeBoard f  left join fetch f.repliesList",
     *     countQuery = "select count(distinct f.bno) from FreeBoard f left join f.repliesList" )
     *     Page<FreeBoard> join04(Pageable page);
     * */
    @Test
    void join04() {
        Pageable pageable = PageRequest.of(1,5 , Sort.Direction.DESC , "bno");
        //countQuery적용
        Page<FreeBoard> page = freeRep.join04(pageable);

        System.out.println("***************************");
        System.out.println("page.getNumber() = "+page.getNumber());
        System.out.println("page.getSize() = "+page.getSize());
        System.out.println("page.getTotalPages() = "+page.getTotalPages());
        System.out.println("page.previousPageable() = "+page.previousPageable());
        System.out.println("page.nextPageable() = "+page.nextPageable());

        System.out.println("page.isFirst() = "+page.isFirst());
        System.out.println("page.isLast() = "+page.isLast());

        System.out.println("page.hasPrevious() = "+page.hasPrevious());
        System.out.println("page.hasNext() = "+page.hasNext());
        System.out.println("*****************************************");

        System.out.println("list.size = " + page.getContent().size());
        //list.forEach(b->System.out.println(b.getBno() +" = " + b.getReplyList().size()));

        page.getContent().forEach(b->{
            System.out.println(b.getBno()+" | " + b.getSubject());
            b.getRepliesList().forEach(r->{
                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
            });
            System.out.println();
        });
    }

    /**
     * QueryDSL를 이용한 join fetch
     * */
    @Test
    public void queryDSL01(){
        QFreeBoard freeBoard = QFreeBoard.freeBoard;
        QReply reply = QReply.reply;

        List<FreeBoard> list = jpaFactory
                .selectFrom(freeBoard)
                .leftJoin(freeBoard.repliesList)
                .fetchJoin()// 조인 방식
                .fetch();// fetch 를 써야지 리스트로 반환한다

        System.out.println("list.size = " + list.size());
        System.out.println("----------------------------");
        // list.forEach(b->System.out.println(b.getBno() +" = " + b.getRepliesList().size()));

        list.forEach(b->{
            System.out.println(b.getBno()+" | " + b.getSubject());
            b.getRepliesList().forEach(r->{
                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
            });
            System.out.println();
        });
    }

    /**
     * QueryDSL를 이용한 join fetch + 페이징처리
     * */
//    @Test
//    public void queryDSL02() {
//        QFreeBoard freeBoard = QFreeBoard.freeBoard;
//        Pageable pageable = PageRequest.of(1,5 , Sort.Direction.DESC , "bno");
//        // Count query
//        long totalCount = jpaFactory
//                // .select(freeBoard.countDistinct())
//                .from(freeBoard)
//                .leftJoin(freeBoard.repliesList) // left join
//                .fetchCount(); // Count distinct bno
//
//        // Data query
//        List<FreeBoard> content = jpaFactory
//                .selectFrom(freeBoard)
//                // .distinct()
//                .leftJoin(freeBoard.repliesList) // left join fetch
//                .fetchJoin() // fetch join
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        Page<FreeBoard> page= new PageImpl<>(content, pageable, totalCount);
//        System.out.println("***************************");
//        System.out.println("page.getNumber() = "+page.getNumber());
//        System.out.println("page.getSize() = "+page.getSize());
//        System.out.println("page.getTotalPages() = "+page.getTotalPages());
//        System.out.println("page.previousPageable() = "+page.previousPageable());
//        System.out.println("page.nextPageable() = "+page.nextPageable());
//
//        System.out.println("page.isFirst() = "+page.isFirst());
//        System.out.println("page.isLast() = "+page.isLast());
//
//        System.out.println("page.hasPrevious() = "+page.hasPrevious());
//        System.out.println("page.hasNext() = "+page.hasNext());
//        System.out.println("*****************************************");
//
//        System.out.println("list.size = " + page.getContent().size());
//        //list.forEach(b->System.out.println(b.getBno() +" = " + b.getReplyList().size()));
//
//        page.getContent().forEach(b->{
//            System.out.println(b.getBno()+" | " + b.getSubject());
//            b.getRepliesList().forEach(r->{
//                System.out.println("====> " +r.getRno()+" | " +r.getContent()+" | "+ r.getRno());
//            });
//            System.out.println();
//        });
//    }

}
