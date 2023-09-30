package com.example.spj.dto;

import com.example.spj.entity.Board;
import com.example.spj.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
@Slf4j
class BoardDtoTest {

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("1. 엔티티 변환 테스트")
    @Test
    void test_1(){

        BoardDto boardDto = BoardDto.builder()
                .board_id(1L)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .title("엔티티변환 테스트")
                .contents("그래그래그래")
                .build();

        log.info("BoardDto == {}" , boardDto.toString());

        Board board = Board.boardDtoToEntity(boardDto);

        log.info("=============================================");
        log.info("Board == {}" , board.toString());

        /*
        * Success 2023.10.01
        *
        * */
    }
}
