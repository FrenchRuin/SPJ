package com.example.spj.service;

import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board saveBoard(Board board, User user) {
        log.info("user >> {}", user);
        log.info("board >> {}", user);

        board.setUser(user);
        board.setCreated(LocalDateTime.now());
        board.setUpdated(LocalDateTime.now());

        return boardRepository.save(board);
    }



}
