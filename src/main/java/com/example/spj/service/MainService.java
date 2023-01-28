package com.example.spj.service;

import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;




}
