package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.FreeBoard;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.service.FreeBoardService;
import web.mvc.service.FreeBoardServiceImpl;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/board")
public class FreeBoardController {

    private final static int PAGE_COUNT=10;
    private final static int BLOCK_COUNT=4;

    private final FreeBoardService boardService;
    private final FreeBoardServiceImpl freeBoardServiceImpl;

    @GetMapping("/list")
    public String list(Model model,@RequestParam(defaultValue = "1")int nowPage) {

        Pageable pageable=PageRequest.of((nowPage-1),PAGE_COUNT, Sort.Direction.DESC,"bno");
        Page<FreeBoard> pageList = freeBoardServiceImpl.selectAll(pageable);
        model.addAttribute("pageList", pageList);// 페이징 처리 정보 + data

        int temp=(nowPage-1)%BLOCK_COUNT;
        int startPage=nowPage-temp;

        model.addAttribute("startPage", startPage);
        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("nowPage", nowPage);

//        List<FreeBoardDTO> freeList = boardService.selectAll();
//        model.addAttribute("freeList", freeList);
        return "board/list";
    }

    @GetMapping("/{url}")
    public String moveToPage(@PathVariable("url") String url) {
        return "board/" + url;
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute FreeBoardDTO boardDTO) {
        boardService.insert(boardDTO);
        return "redirect:/board/list";
    }

    @GetMapping("/read/{bno}")
    public String read(@PathVariable Long bno, Model model) {
        FreeBoardDTO board = boardService.selectBy(bno, true);
        model.addAttribute("board", board);
        return "board/read";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam("bno") Long bno, Model model) {
        FreeBoardDTO board = boardService.selectBy(bno, false);
        model.addAttribute("board", board);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute FreeBoardDTO boardDTO) {
        log.info("board update 도착");
        boardService.update(boardDTO);
        return "redirect:/board/read/" + boardDTO.getBno();
    }

    @PostMapping("/delete")
    public String delete(Long bno, String password) {
        boardService.delete(bno, password);
        return "redirect:/board/list";
    }
}
