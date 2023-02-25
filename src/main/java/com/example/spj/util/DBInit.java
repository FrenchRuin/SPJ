package com.example.spj.util;

import com.example.spj.config.UserService;
import com.example.spj.entity.board.Board;
import com.example.spj.entity.user.User;
import com.example.spj.service.ToyProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DBInit implements InitializingBean {

    private final UserService userService;
    private final ToyProjectService toyProjectService;


    @Override
    public void afterPropertiesSet() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userService.findUser("toxic023").isEmpty()) {
            User director = userService.saveUser(
                    User.builder()
                            .username("toxic023")
                            .password(passwordEncoder.encode("as2646"))
                            .enabled(true)
                            .build()
            );
            userService.addAuthority(director.getUserId(), "ROLE_ADMIN");
            Board board = Board.builder()
                    .board_id(1L)
                    .contents("끝내줍니다.")
                    .created(LocalDateTime.now())
                    .title("테스트용")
                    .build();
            toyProjectService.saveBoard(board, director);
        }
    }
}
