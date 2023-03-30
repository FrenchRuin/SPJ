package com.example.spj.controller;

import com.example.spj.config.UserService;
import com.example.spj.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("error",false);
        return "login";
    }

    @GetMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login";
    }

    @PostMapping("/signUpProcess")
    public String signUpProcess(Model model, User user) {
        model.addAttribute("user", user);
        userService.signUp(user);
        return "redirect:/login";
    }

    @GetMapping("/auth")
    @ResponseBody
    public Authentication auth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
