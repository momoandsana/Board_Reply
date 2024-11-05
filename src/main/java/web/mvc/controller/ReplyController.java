package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.Reply;
import web.mvc.dto.ReplyDTO;
import web.mvc.service.FreeBoardService;
import web.mvc.service.ReplyService;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;

    private final FreeBoardService freeBoardService;

    private final ModelMapper modelMapper;

    @PostMapping("/writeForm")
    public String writeForm(@RequestParam("bno") Long bno, Model model) {
        model.addAttribute("bno", bno);
        return "reply/write";
    }

//    @PostMapping("/insert")
//    public String insert(@ModelAttribute Reply reply, @RequestParam("bno") Long bno) {
//        FreeBoard freeBoard = new FreeBoard();
//        freeBoard.setBno(bno);
//        reply.setFreeBoard(freeBoard);
//
//        replyService.insert(reply);
//
//        return "redirect:/board/read/"+bno;
//    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute ReplyDTO replyDTO, @RequestParam("bno") Long bno, ModelMap modelMap) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);// 원래는 서비스에서 변환한다
        reply.setFreeBoard(new FreeBoard(bno));// FreeBoard 를 객체로 넣어줘야 한다

        replyService.insert(reply);

        return "redirect:/board/read/"+bno;
    }

    @GetMapping("/delete/{rno}/{bno}")
    public String delete(@PathVariable("rno") Long rno,
                         @PathVariable("bno") Long bno)
    {
        replyService.delete(rno);
        return "redirect:/board/read/"+bno+"?state=false";// 댓글 삭제후 부모글 상세보기로 이동,조회수는 증가x
    }
}

/*
조회할 때는 포워딩, 나머지는 리다이렉트
 */
