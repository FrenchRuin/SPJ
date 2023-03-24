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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /* Board Section */

    @GetMapping("/toy/board")
    public String board(Model model, @RequestParam(value = "wrongUser", required = false , defaultValue = "true") boolean wrongUser) {
        model.addAttribute("prevPage", "/toy");
        model.addAttribute("boardList", toyProjectService.findAllBoard());
        model.addAttribute("wrongUser", wrongUser);
        return "toy/board";
    }

    @GetMapping("/toy/findBoard")
    @ResponseBody
    public Optional<Board> findBoard(Board board) {
        return toyProjectService.findBoard(board.getBoard_id());
    }

    @PostMapping("/toy/saveBoard")
    public String addBoard(Model model, RedirectAttributes re, Board board, User user) {
        model.addAttribute("board", board);
        model.addAttribute("user", user);

        log.info("{}",user);

        User saveUser = toyProjectService.saveBoard(board, user);
        if (saveUser != null)
            re.addAttribute("wrongUser", true);

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
    public String karlo() {
        return "/toy/karlo";
    }

    @PostMapping("/toy/getKarloImage")
    @ResponseBody
    public String getKarloImage(@RequestParam(name = "requested") String requested){
        log.info("requested >> {}" , requested);
        return toyProjectService.getKarloImage(requested);
    }
}
