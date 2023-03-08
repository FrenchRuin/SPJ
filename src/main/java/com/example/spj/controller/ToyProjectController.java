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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/toy/findBoard")
    @ResponseBody
    public Optional<Board> findBoard(Model model, Board board){
        return toyProjectService.findBoard(board.getBoard_id());
    }

    @PostMapping("/toy/saveBoard")
    public String addBoard(Model model, Board board, User user) {
        model.addAttribute("board", board);
        model.addAttribute("user", user);
        toyProjectService.saveBoard(board, user);
        return "redirect:/toy/board";
    }
}
