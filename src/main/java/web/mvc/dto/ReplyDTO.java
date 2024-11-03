package web.mvc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@ToString
@Getter
//@AllArgsConstructor
public class ReplyDTO   {
    private Long rno;//댓글번호
	private String content;//댓글내용
	private LocalDateTime insertDate;
	
	
	public ReplyDTO(Long rno, String content, LocalDateTime insertDate) {
		super();
		this.rno = rno;
		this.content = content;
		this.insertDate = insertDate;
	}
	

}
