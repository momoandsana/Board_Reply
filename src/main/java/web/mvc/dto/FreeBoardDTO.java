package web.mvc.dto;

import lombok.Getter;
import lombok.Setter;
import web.mvc.domain.FreeBoard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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

	public static FreeBoardDTO from(FreeBoard freeBoard) {
		FreeBoardDTO dto = new FreeBoardDTO();
		dto.setBno(freeBoard.getBno());
		dto.setSubject(freeBoard.getSubject());
		dto.setWriter(freeBoard.getWriter());
		dto.setContent(freeBoard.getContent());
		dto.setPassword(freeBoard.getPassword());
		dto.setReadnum(freeBoard.getReadnum());
		dto.setInsertDate(freeBoard.getInsertDate());
		// 필요 시 repliesList 변환
		if (freeBoard.getRepliesList() != null) {
			dto.setRepliesList(
					freeBoard.getRepliesList().stream()
							.map(ReplyDTO::from)
							.collect(Collectors.toList())
			);
		}
		return dto;
	}

}
