package web.mvc.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.mvc.dto.ReplyDTO;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_rno_seq")
	@SequenceGenerator(name="reply_rno_seq", allocationSize = 1, sequenceName = "reply_rno_seq")
	private Long rno;

	private String content;

	@CreationTimestamp
	private LocalDateTime insertDate;

	@ManyToOne(fetch = FetchType.LAZY)// default 는 eager 다대일이니까
	@JoinColumn(name = "free_bno")// fk 를 지정, 원래는 freeBoard 로 fk로 잡히는데 수정함
	private FreeBoard freeBoard;

	// ReplyDTO를 Reply 엔티티로 변환하는 정적 메서드
	public static Reply from(ReplyDTO dto) {
		return Reply.builder()
				.rno(dto.getRno())
				.content(dto.getContent())
				.insertDate(dto.getInsertDate())
				.build();
	}
}
