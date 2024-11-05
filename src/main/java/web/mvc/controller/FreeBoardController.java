package web.mvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.service.FreeBoardService;
import web.mvc.service.FreeBoardServiceImpl;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/board")
public class FreeBoardController {

    private final static int PAGE_COUNT=5;
    private final static int BLOCK_COUNT=4;

    private final FreeBoardService boardService;
    private final FreeBoardServiceImpl freeBoardServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping("/list")
    public String list(Model model,@RequestParam(defaultValue = "1")int nowPage) {

        Pageable pageable=PageRequest.of((nowPage-1),PAGE_COUNT, Sort.Direction.ASC,"bno");
        Page<FreeBoardDTO> pageList = freeBoardServiceImpl.selectAll(pageable);
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

        return "redirect:/board/list";// prg 방식. 등록을 하고 다시 리스트로 보냄. post redirect get
    }

    @GetMapping("/read/{bno}")
    public String read(@PathVariable Long bno, Model model,
                       @RequestParam(required = false,defaultValue ="true") Boolean state) {
        FreeBoardDTO board = boardService.selectBy(bno, state);// state 로 조회수 증가
        model.addAttribute("board", board); // 해당 글 번호에 해당하는 글을 가지고 옴
        return "board/read";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam("bno") Long bno, Model model) {
        FreeBoardDTO board = boardService.selectBy(bno, false);// 여기서는 조회수가 올라가지 않는다
        model.addAttribute("board", board);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute FreeBoardDTO boardDTO) {
        log.info("board update 도착");
        boardService.update(boardDTO);
        return "redirect:/board/read/" + boardDTO.getBno();
    }
    /*
    여러 번 수행해도 결과가 같은(prg)를 유지하기 위해 리다이렉션 사용
     */

    @PostMapping("/delete")
    public String delete(Long bno, String password) {
        boardService.delete(bno, password);
        return "redirect:/board/list";
    }
}

/*
<페이징 처리 원리>

게시글 수=페이지*블록

페이지(Page): 데이터를 일정한 수량으로 나눈 단위입니다. 예를 들어, 한 페이지에 5개의 게시글을 표시하면, 총 20개의 게시글은 4개의 페이지로 나뉩니다.
블록(Block): 페이지 링크를 그룹으로 묶는 단위입니다. 예를 들어, 한 블록에 4개의 페이지 링크를 표시하면, 총 20개의 페이지는 5개의 블록으로 나뉩니다.
페이지당 항목 수(PAGE_COUNT): 한 페이지에 표시할 데이터의 수입니다.
블록당 페이지 수(BLOCK_COUNT): 한 블록에 표시할 페이지 링크의 수입니다

PAGE_COUNT: 한 페이지에 표시할 게시글 수 (여기서는 5개).
BLOCK_COUNT: 한 블록에 표시할 페이지 링크 수 (여기서는 4개).
nowPage: 현재 사용자가 보고 있는 페이지 번호 (기본값은 1).
페이징 로직 설명
Pageable 객체 생성:

PageRequest.of(page, size, sort) 메서드를 사용하여 Pageable 객체를 생성합니다.
page: 0부터 시작하는 페이지 번호. nowPage - 1로 설정하여 사용자가 보는 페이지 번호(1부터 시작)를 Pageable에 맞게 변환합니다.
size: 한 페이지에 표시할 게시글 수 (PAGE_COUNT).
sort: 정렬 기준 (Sort.Direction.DESC, "bno").
Page<FreeBoard> 가져오기:

freeBoardServiceImpl.selectAll(pageable)을 호출하여 페이징된 데이터를 가져옵니다.
결과는 Page<FreeBoard> 타입으로, 현재 페이지의 데이터와 페이징 정보가 포함됩니다.
블록의 시작 페이지 계산:

temp = (nowPage - 1) % BLOCK_COUNT는 현재 페이지가 속한 블록 내에서의 위치를 계산합니다.
startPage = nowPage - temp는 현재 페이지가 속한 블록의 시작 페이지 번호를 계산합니다.
예를 들어:

nowPage = 7, BLOCK_COUNT = 4일 때:
temp = (7 - 1) % 4 = 6 % 4 = 2
startPage = 7 - 2 = 5
즉, 페이지 5부터 8까지가 한 블록이 됩니다.
모델에 변수 추가:

pageList: 페이징된 데이터 및 페이징 정보.
startPage: 현재 블록의 시작 페이지 번호.
blockCount: 한 블록에 표시할 페이지 링크 수.
nowPage: 현재 페이지 번호.
 */
