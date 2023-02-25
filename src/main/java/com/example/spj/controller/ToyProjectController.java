package com.example.spj.controller;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.service.ToyProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ToyProjectController {

    private final ToyProjectService toyProjectService;

    @GetMapping("/toy")
    public String main(Model model) {
        model.addAttribute("prevPage", "/main");
        return "toy/toyIndex";
    }

    @GetMapping("/toy/board")
    public String board(Model model) {
        model.addAttribute("prevPage", "/toy");
        model.addAttribute("boardList", toyProjectService.findAllBoard());
        return "toy/board";
    }

    @PostMapping("/toy/saveBoard")
    public String addBoard(Model model, Board board, User user) {
        model.addAttribute("board", board);
        model.addAttribute("user", user);
        toyProjectService.saveBoard(board, user);
        return "redirect:/toy/board";
    }
}
