package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.Reply;
import web.mvc.service.ReplyService;

@Controller
@AllArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reply/writeForm")
    public String writeForm(@RequestParam("bno") Long bno, Model model) {
        model.addAttribute("bno", bno);
        return "reply/write";
    }

    @PostMapping("/reply/insert")
    public String insert(@ModelAttribute Reply reply, @RequestParam("bno") Long bno) {
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setBno(bno);
        reply.setFreeBoard(freeBoard);

        replyService.insert(reply);

        return "redirect:/board/read/"+bno;
    }

    @GetMapping("/reply/delete/{rno}/{bno}")
    public String delete(@PathVariable("rno") Long rno,@PathVariable("bno") Long bno) {
        replyService.delete(rno);
        return "redirect:/board/read/"+bno;// 댓글 삭제후 부모글 상세보기로 이동
    }
}
