package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Reply;
import web.mvc.service.ReplyService;

@Controller
@AllArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/reply/writeForm")
    public String writeForm() {
        return "reply/writeForm";
    }

    @PostMapping("/reply/insert")
    public String insert(@ModelAttribute Reply reply, @RequestParam("bno") Long bno) {
        replyService.insert(reply);
        return "redirect:/reply/read/"+bno;
    }

    @GetMapping("/reply/delete/{rno}/{bno}")
    public String delete(@PathVariable("rno") Long rno,@PathVariable("bno") Long bno) {
        replyService.delete(rno);
        return "redirect:/reply/read/"+bno;// 댓글 삭제후 부모글 상세보기로 이동
    }
}
