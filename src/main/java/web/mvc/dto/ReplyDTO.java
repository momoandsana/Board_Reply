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
