package com.example.spj.service;

import com.example.spj.entity.Board;
import com.example.spj.entity.User;
import com.example.spj.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MainServiceTest {

    @Autowired
    private BoardRepository boardRepository;


    @DisplayName("1. 게시글 생성")
    @Test
    void test_1(){

        Board board = new Board();

        board.setBoard_id(1L);
        board.setContents("테스트에요");
        board.setTitle("테스트");
        User user = new User();

        user.setUserId(1L);
        user.setUsername("유저1");
        board.setUser(user);
        boardRepository.save(board);

        log.info("board >>> {}", board.getUser());
    }

}