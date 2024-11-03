package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public String read(@PathVariable Long bno,Model model){
        FreeBoard board=boardService.selectBy(bno,true);
        model.addAttribute("board",board);
        return "board/read";
    }



}
