package web.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
