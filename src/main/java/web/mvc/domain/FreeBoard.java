package web.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.dto.FreeBoardDTO;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@DynamicUpdate// 조회수만 set 으로 변경감지하면 update 문이 readnum 에 대해서만 올라감
public class FreeBoard { //db에 free_board
	@Id //pk
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "free_bno_seq")
	@SequenceGenerator(name ="free_bno_seq" , allocationSize = 1 , sequenceName = "free_bno_seq")
	private Long bno;
	private String subject;
	private String writer;

	@Column(length = 500)
	private String content;
	private String password;
	private int readnum; //조회수

	@CreationTimestamp
	private LocalDateTime insertDate; //등록일

	@UpdateTimestamp
	private LocalDateTime updateDate; //수정

	@OneToMany(mappedBy = "freeBoard" , cascade = CascadeType.ALL)
	// 양방향 관계, Reply 엔티티의 freeBoard 변수 이름 사용해서 매핑
	private List<Reply> repliesList;//  지연로딩

	public FreeBoard(Long bno) {
		this.bno = bno;
	}

	// FreeBoardDTO를 FreeBoard 엔티티로 변환하는 from 정적 메서드
	public static FreeBoard from(FreeBoardDTO dto) {
		FreeBoard freeBoard = FreeBoard.builder()
				.bno(dto.getBno())
				.subject(dto.getSubject())
				.writer(dto.getWriter())
				.content(dto.getContent())
				.password(dto.getPassword())
				.readnum(dto.getReadnum())
				.insertDate(dto.getInsertDate())
				.build();
		// 필요 시 repliesList 변환 추가 가능
		return freeBoard;
	}
}
/*
디비에서는 댓글을 검색하면 부모글의 정보를 검색하고 싶다
select * from board join reply on

게시물을 검색하면 게시물에 달린 댓글을 검색하고 싶다
select * from board join reply on

java 에서

public Reply1(){
댓글중심일 때

public Board2(){
게시글 중심일 때->Board 는 mappedBy 가 없으면 reply 에 대한 정보가 없음

 */
