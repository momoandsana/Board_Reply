package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.FreeBoard;
import web.mvc.service.FreeBoardService;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class FreeBoardController {

    private final FreeBoardService boardService;

    @GetMapping("/board/list")
    public String list(Model model) {
        List<FreeBoard> freeList=boardService.selectAll();
        model.addAttribute("freeList",freeList);
        return "board/list";
    }

    @GetMapping("/board/{url}")
    public String moveToPage(@PathVariable("url") String url)
    {
        return "board/"+url;
    }

    @PostMapping("/board/insert")
    public String insert(@ModelAttribute FreeBoard board)
    {
        boardService.insert(board);
        return "redirect:/board/list";
    }

    @GetMapping("/board/read/{bno}")
    public String read(@PathVariable Long bno,Model model){
        FreeBoard board=boardService.selectBy(bno,true);
        model.addAttribute("board",board);
        return "board/read";
    }

    @PostMapping("/board/updateForm")
    public String updateForm(@RequestParam("bno") Long bno, Model model)
    {
        FreeBoard board=boardService.selectBy(bno,false);
        model.addAttribute("board",board);
        return "board/update";
    }



    @PostMapping("/board/update")
    public String update(@ModelAttribute FreeBoard board)
    {
        log.info("board update 도착");
        boardService.update(board);
        return "redirect:/board/read/"+board.getBno();
    }// reply 기능 구현 후 다시 테스트
    /*
    2024-11-03T11:35:29.269+09:00  WARN 18804 --- [Board_Reply] [nio-9000-exec-2] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported]
     */

    @PostMapping("/board/delete")
    public String delete(Long bno,String password)
    {
        boardService.delete(bno,password);
        return "redirect:/board/list";
    }





}
