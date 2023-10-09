package com.example.spj.controller;

import com.example.spj.dto.BoardDto;
import com.example.spj.dto.UserDto;
import com.example.spj.entity.Board;
import com.example.spj.entity.User;
import com.example.spj.service.ToyProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ToyProjectController {

    private final ToyProjectService toyProjectService;

    @GetMapping("/toy/board")
    public String board(
            Model model,
            @AuthenticationPrincipal User user // Principal
    ) {
        model.addAttribute("prevPage", "/");
        model.addAttribute("boardList", toyProjectService.findAllBoard());
        model.addAttribute("username", user.getUsername());
        return "toy/board";
    }

    @GetMapping("/toy/findBoard")
    @ResponseBody
    public Optional<Board> findBoard(Board board) {
        return toyProjectService.findBoard(board.getBoard_id());
    }

    @PostMapping("/toy/saveBoard")
    public String addBoard(Model model, BoardDto boardDto, UserDto userDto) {
        toyProjectService.saveBoard(boardDto , userDto);
        return "redirect:/toy/board";
    }

    @GetMapping("/toy/deleteBoard")
    public String deleteBoard(Board board) {
        log.info("{}", board);
        toyProjectService.deleteBoard(board);
        return "/toy/board";
    }

    /* karlo API Section*/

    @GetMapping("/toy/karlo")
    public String karlo(Model model) {
        model.addAttribute("prevPage", "/");
        return "/toy/karlo";
    }

    @PostMapping("/toy/getKarloImage")
    @ResponseBody
    public String getKarloImage(@RequestParam(name = "requested") String requested){
        log.info("requested >> {}" , requested);
        return toyProjectService.getKarloImage(requested);
    }

    /* Naver API Section
    *
    *  Papago
    * */

    @GetMapping("/toy/papago")
    public String papago(Model model) {
        model.addAttribute("prevPage", "/");
        return "/toy/papago";
    }

    @PostMapping("/toy/getPapagoTranslate")
    @ResponseBody
    public String getPapagoTranslate(
            @RequestParam(name = "source") String source,
            @RequestParam(name = "target") String target
            ,@RequestParam(name = "requested") String requested
    ){
        return toyProjectService.getPapago(source, target, requested);
    }
}
