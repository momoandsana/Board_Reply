package web.mvc.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
public class FreeBoardDTO {
	private Long bno;
	private String subject;
	private String writer;
	private String content;
	private String password;
	private int readnum; //조회수
	private LocalDateTime insertDate; //등록일
	private List<ReplyDTO> repliesList;
	
	
	

}
