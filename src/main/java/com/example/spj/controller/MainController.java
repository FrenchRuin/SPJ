package com.example.spj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @GetMapping("/devInfo")
    public String devInfo() {
        return "devInfo";
    }



}
