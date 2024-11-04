package web.mvc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import web.mvc.domain.Reply;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@ToString
@Getter
public class ReplyDTO {
	private Long rno;
	private String content;
	private LocalDateTime insertDate;

	public ReplyDTO(Long rno, String content, LocalDateTime insertDate) {
		this.rno = rno;
		this.content = content;
		this.insertDate = insertDate;
	}

	public static ReplyDTO from(Reply reply) {
		return new ReplyDTO(
				reply.getRno(),
				reply.getContent(),
				reply.getInsertDate()
		);
	}
}
/*
ReplyDTO 와 FreeBoardDTO 가 순환참조하지 않기 위해서는 from 함수에서 서로 객체를 참조하지 말고 bno 같이 특정 부분만 참조해야 한다
 */
