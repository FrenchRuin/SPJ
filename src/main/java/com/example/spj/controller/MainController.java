package com.example.spj.controller;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/devInfo")
    public String devInfo() {
        return "devInfo";
    }

    @GetMapping("/board")
    public String board(Model model, Board board) {
        model.addAttribute("board", board);
        return "board";
    }

    @PostMapping("/saveBoard")
    public String saveBoard(Model model, Board board , Authentication authentication) {
        model.addAttribute("board", board);
        log.info("board >>>> {}", board);
        log.info("user >>>> {}" , authentication);
        return "redirect:/board";
    }
}
