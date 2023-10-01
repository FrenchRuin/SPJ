package com.example.spj.dto;

import com.example.spj.entity.Board;
import com.example.spj.entity.User;
import com.example.spj.repository.BoardRepository;
import com.example.spj.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@SpringBootTest
@Slf4j
class BoardDtoTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    static BoardDto boardDto;

    @BeforeEach
    void createData (){

    }

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
        * */
    }


    @Transactional(readOnly = true) /*Cause LazyInitializationException*/
    @DisplayName("2. 엔티티변환후 repository save Test")
    @Test
    void test_2(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setPassword(passwordEncoder.encode("dddd"));
        userDto.setCreatedTime(LocalDateTime.now());
        userDto.setUpdatedTime(LocalDateTime.now());
        userDto.setUsername("admin");
        userDto.setEnabled(true);

        User user = User.userDtoToEntity(userDto);
        userRepository.save(user);

        boardDto = BoardDto.builder()
                .board_id(1L)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .title("엔티티변환 테스트")
                .contents("그래그래그래")
                .user(userDto)
                .build();

        Board board = Board.boardDtoToEntity(boardDto);

        boardRepository.save(board);

        Optional<Board> boardTest = boardRepository.findById(1L);

        log.info("BOARD ENTITY ==== {}" , boardTest);

        /*
         SUCCESS 2023.10.02
        * */

    }

}
